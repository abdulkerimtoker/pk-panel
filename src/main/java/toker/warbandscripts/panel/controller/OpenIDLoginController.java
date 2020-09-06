package toker.warbandscripts.panel.controller;

import com.auth0.jwt.JWT;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.openid.OpenIDAuthenticationStatus;
import org.springframework.security.openid.OpenIDAuthenticationToken;
import org.springframework.security.openid.OpenIDConsumer;
import org.springframework.security.openid.OpenIDConsumerException;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import toker.warbandscripts.panel.entity.PanelUser;
import toker.warbandscripts.panel.entity.PanelUserAuthorityAssignment;
import toker.warbandscripts.panel.entity.PanelUserSession;
import toker.warbandscripts.panel.repository.PanelUserSessionRepository;
import toker.warbandscripts.panel.service.PanelUserService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

import static com.auth0.jwt.algorithms.Algorithm.HMAC512;

@RestController
public class OpenIDLoginController {
    private static final String OPENID_CLAIMED_IDENTITY = "https://steamcommunity.com/openid";

    private OpenIDConsumer consumer;

    private PanelUserService panelUserService;

    private PanelUserSessionRepository sessionRepo;

    public OpenIDLoginController(OpenIDConsumer consumer,
                                 PanelUserService panelUserService,
                                 PanelUserSessionRepository sessionRepo) {
        this.consumer = consumer;
        this.panelUserService = panelUserService;
        this.sessionRepo = sessionRepo;
    }

    @RequestMapping("/api/login")
    public ResponseEntity login(HttpServletRequest request) throws OpenIDConsumerException {
        String requestUrl = request.getRequestURL().toString();
        String redirectTo = consumer.beginConsumption(request,
                OPENID_CLAIMED_IDENTITY,
                requestUrl.replace(request.getServletPath(), "/processLogin"),
                lookupRealm(requestUrl));
        return ResponseEntity.status(HttpStatus.OK)
                .header(HttpHeaders.LOCATION, redirectTo)
                .build();
    }

    @RequestMapping("/api/processLogin")
    public ResponseEntity processLogin(HttpServletRequest request) throws OpenIDConsumerException {
        OpenIDAuthenticationToken token = consumer.endConsumption(new HttpServletRequestWrapper(request) {
            @Override
            public StringBuffer getRequestURL() {
                return new StringBuffer(request.getRequestURL().toString()
                        .replace("api/processLogin", "processLogin"));
            }
        });

        request.getSession().invalidate();

        if (token.getStatus() == OpenIDAuthenticationStatus.SUCCESS) {
            PanelUser panelUser = panelUserService.getOrCreateForClaimedIdentity(token.getIdentityUrl());

            if (panelUser != null) {
                PanelUserSession session = new PanelUserSession();
                session.setUser(panelUser);
                session = sessionRepo.save(session);

                Collection<PanelUserAuthorityAssignment> authorities =
                        panelUser.getAuthorityAssignments();
                List<String> authorizations = authorities != null ?
                        panelUser.getAuthorityAssignments()
                                .stream()
                                .map(assignment -> String.format(
                                        "ROLE_%d_%s",
                                        assignment.getServer().getId(),
                                        assignment.getAuthority().getAuthorityName()
                                ))
                                .collect(Collectors.toList())
                        : new LinkedList<>();

                String jwt = JWT.create()
                        .withSubject(panelUser.getUsername())
                        .withClaim("Identity", token.getIdentityUrl())
                        .withClaim("Session-ID", session.getId())
                        .withClaim("Authorizations", authorizations)
                        .sign(HMAC512("sea".getBytes()));

                return ResponseEntity.status(HttpStatus.ACCEPTED)
                        .header(HttpHeaders.AUTHORIZATION, "Bearer "  + jwt)
                        .build();
            }
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
    }

    private String lookupRealm(String requestUrl) {
        try {
            URL url = new URL(requestUrl);
            int port = url.getPort();
            StringBuilder realmBuffer = new StringBuilder()
                    .append(url.getProtocol()).append("://").append(url.getHost());
            if (port > 0) {
                realmBuffer.append(":").append(port);
            }
            realmBuffer.append("/");
            return realmBuffer.toString();
        } catch (MalformedURLException e) {
            e.printStackTrace();
            return null;
        }
    }
}
