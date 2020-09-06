package toker.warbandscripts.panel.controller.rest;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import toker.warbandscripts.panel.authentication.EndedSessions;
import toker.warbandscripts.panel.authentication.JWTOpenIDAuthenticationToken;
import toker.warbandscripts.panel.entity.PanelUserSession_;
import toker.warbandscripts.panel.repository.PanelUserSessionRepository;

@RestController
public class LogoutController {

    private PanelUserSessionRepository sessionRepo;

    public LogoutController(PanelUserSessionRepository sessionRepo) {
        this.sessionRepo = sessionRepo;
    }

    @GetMapping("/api/logout")
    public void logout() {
        JWTOpenIDAuthenticationToken token =
                (JWTOpenIDAuthenticationToken) SecurityContextHolder.getContext()
                .getAuthentication();

        sessionRepo.findOne((root, query, builder) -> builder.equal(root.get(PanelUserSession_.id), token.getSessionId()))
                .ifPresent(session -> {
                    session.setEnded(true);
                    sessionRepo.save(session);
                });

        EndedSessions.endSession(token.getSessionId());
    }
}
