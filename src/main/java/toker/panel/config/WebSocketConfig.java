package toker.panel.config;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.simp.config.ChannelRegistration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.messaging.simp.stomp.StompCommand;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.messaging.support.ChannelInterceptor;
import org.springframework.messaging.support.MessageHeaderAccessor;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;
import toker.panel.authentication.BareGrantedAuthority;
import toker.panel.authentication.JWTOpenIDAuthenticationToken;

import java.util.List;
import java.util.stream.Collectors;

@Configuration
@EnableWebSocketMessageBroker
@Order(Ordered.HIGHEST_PRECEDENCE + 99)
public class WebSocketConfig implements WebSocketMessageBrokerConfigurer {

    @Override
    public void configureMessageBroker(MessageBrokerRegistry config) {
        config.enableSimpleBroker("/channel");
        config.setApplicationDestinationPrefixes("/");
    }

    @Override
    public void registerStompEndpoints(StompEndpointRegistry registry) {
        registry.addEndpoint("/ws")
                .withSockJS();
    }

    @Override
    public void configureClientInboundChannel(ChannelRegistration registration) {
        registration.interceptors(new ChannelInterceptor() {
            @Override
            public Message<?> preSend(Message<?> message, MessageChannel channel) {
                StompHeaderAccessor accessor =
                        MessageHeaderAccessor.getAccessor(message, StompHeaderAccessor.class);
                if (StompCommand.CONNECT.equals(accessor.getCommand())) {
                    String jwt = accessor.getFirstNativeHeader("Authorization");
                    if (jwt != null && jwt.startsWith("Bearer ")) {
                        jwt = jwt.replace("Bearer ", "");
                        try {
                            DecodedJWT decodedJWT = JWT.require(Algorithm.HMAC512("sea".getBytes()))
                                    .build()
                                    .verify(jwt);

                            int sessionId = decodedJWT.getClaim("Session-ID").asInt();

                            String username = decodedJWT.getSubject();
                            String claimedIdentity = decodedJWT.getClaim("Identity").asString();

                            List<BareGrantedAuthority> authorityList = decodedJWT.getClaim("Authorizations")
                                    .asList(String.class)
                                    .stream()
                                    .map(BareGrantedAuthority::new)
                                    .collect(Collectors.toList());

                            Integer selectedServerId = null;
                            String selectedServerIdStr = accessor.getFirstNativeHeader("SelectedServerId");

                            if (selectedServerIdStr != null &&
                                    authorityList.stream().map(BareGrantedAuthority::getAuthority)
                                            .collect(Collectors.toList())
                                            .contains(String.format("ROLE_%s_USER", selectedServerIdStr))) {
                                selectedServerId = Integer.parseInt(selectedServerIdStr);
                            }

                            JWTOpenIDAuthenticationToken token =
                                    new JWTOpenIDAuthenticationToken(
                                            authorityList, username, jwt,
                                            claimedIdentity, selectedServerId,
                                            sessionId);
                            accessor.setUser(token);
                        }
                        catch (JWTVerificationException ignored) {}
                    }
                }
                return message;
            }
        });
    }
}
