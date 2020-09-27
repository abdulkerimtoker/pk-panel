package toker.panel.controller.rest

import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController
import toker.panel.authentication.EndedSessions
import toker.panel.authentication.JWTOpenIDAuthenticationToken
import toker.panel.entity.PanelUserSession
import toker.panel.entity.PanelUserSession_
import toker.panel.repository.PanelUserSessionRepository
import javax.persistence.criteria.CriteriaBuilder
import javax.persistence.criteria.CriteriaQuery
import javax.persistence.criteria.Root

@RestController
class LogoutController(private val sessionRepo: PanelUserSessionRepository) {
    @GetMapping("/api/logout")
    fun logout() {
        val token = SecurityContextHolder.getContext()
                .authentication as JWTOpenIDAuthenticationToken
        sessionRepo.findOne { root: Root<PanelUserSession?>, _, builder: CriteriaBuilder ->
            builder.equal(root.get(PanelUserSession_.id), token.sessionId) }
                .ifPresent { session: PanelUserSession ->
                    session.isEnded = true
                    sessionRepo.save(session)
                }
        EndedSessions.endSession(token.sessionId)
    }
}