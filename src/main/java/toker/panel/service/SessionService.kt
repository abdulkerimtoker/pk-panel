package toker.panel.service

import org.springframework.scheduling.annotation.Scheduled
import org.springframework.stereotype.Service
import toker.panel.authentication.EndedSessions
import toker.panel.entity.PanelUserSession
import toker.panel.entity.PanelUserSession_
import toker.panel.repository.PanelUserSessionRepository
import java.util.stream.Collectors
import javax.persistence.criteria.CriteriaBuilder
import javax.persistence.criteria.Root

@Service
class SessionService(
    private val sessionRepository: PanelUserSessionRepository
) {
    @Scheduled(fixedDelay = 5 * 1000)
    fun endSessionsPeriodically() {
        val endedSessions = sessionRepository.findAll { root: Root<PanelUserSession?>, _, builder: CriteriaBuilder ->
            builder.equal(root.get(PanelUserSession_.isEnded), true) }
        EndedSessions.endSessions(endedSessions.stream().map { it!!.id }.collect(Collectors.toList()))
    }
}