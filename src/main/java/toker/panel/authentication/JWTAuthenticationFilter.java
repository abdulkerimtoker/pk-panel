package toker.panel.authentication;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

public class JWTAuthenticationFilter extends BasicAuthenticationFilter {

    public JWTAuthenticationFilter(AuthenticationManager authenticationManager) {
        super(authenticationManager);
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain chain) throws IOException, ServletException {
        String jwt = request.getHeader("Authorization");

        if (jwt != null && jwt.startsWith("Bearer ")) {
            jwt = jwt.replace("Bearer ", "");
            try {
                DecodedJWT decodedJWT = JWT.require(Algorithm.HMAC512("sea".getBytes()))
                        .build()
                        .verify(jwt);

                int sessionId = decodedJWT.getClaim("Session-ID").asInt();

                if (EndedSessions.isSessionEnded(sessionId))
                    throw new Exception();

                String username = decodedJWT.getSubject();
                String claimedIdentity = decodedJWT.getClaim("Identity").asString();

                List<BareGrantedAuthority> authorityList = decodedJWT.getClaim("Authorizations")
                        .asList(String.class)
                        .stream()
                        .map(BareGrantedAuthority::new)
                        .collect(Collectors.toList());

                Integer selectedServerId = null;
                String selectedServerIdStr = request.getHeader("Selected-Server-ID");

                if (selectedServerIdStr != null &&
                        authorityList.stream().map(BareGrantedAuthority::getAuthority)
                                .collect(Collectors.toList())
                                .contains(String.format("ROLE_%s_USER", selectedServerIdStr))) {
                    selectedServerId = Integer.parseInt(selectedServerIdStr);
                }

                SecurityContextHolder.getContext().setAuthentication(
                        new JWTOpenIDAuthenticationToken(
                                authorityList, username, jwt,
                                claimedIdentity, selectedServerId,
                                sessionId));
            }
            catch (Exception ignored) {}
        }

        chain.doFilter(request, response);
    }

    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) {
        return !request.getServletPath().startsWith("/api") ||
                request.getServletPath().equals("/api/login") ||
                request.getServletPath().equals("/api/processLogin");
    }
}
