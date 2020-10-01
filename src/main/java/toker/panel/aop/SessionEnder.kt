package toker.panel.aop

import org.aspectj.lang.annotation.Aspect
import org.aspectj.lang.annotation.Before
import org.aspectj.lang.annotation.Pointcut
import toker.panel.authentication.EndedSessions
import toker.panel.entity.PanelUserAuthorityAssignment
import toker.panel.entity.PanelUserSession
import toker.panel.entity.PanelUserSession_
import toker.panel.entity.PanelUser_
import toker.panel.repository.PanelUserAuthorityAssignmentRepository
import toker.panel.repository.PanelUserRepository
import toker.panel.repository.PanelUserSessionRepository
import java.util.function.Consumer
import javax.persistence.criteria.CriteriaBuilder
import javax.persistence.criteria.CriteriaQuery
import javax.persistence.criteria.Root

@Aspect
class SessionEnder {
    private lateinit var panelUserRepository: PanelUserRepository
    private lateinit var sessionRepository: PanelUserSessionRepository
    private lateinit var assignmentRepository: PanelUserAuthorityAssignmentRepository

    fun setPanelUserRepository(panelUserRepository: PanelUserRepository) {
        this.panelUserRepository = panelUserRepository
    }

    fun setSessionRepository(sessionRepository: PanelUserSessionRepository) {
        this.sessionRepository = sessionRepository
    }

    fun setAssignmentRepository(assignmentRepository: PanelUserAuthorityAssignmentRepository) {
        this.assignmentRepository = assignmentRepository
    }

    @Pointcut("execution(* toker.panel.service.PanelUserService.assignAuthority(int, int, int)) && " +
            "args(serverId, adminId, authorityId)")
    fun authorityAssignment(serverId: Int, adminId: Int, authorityId: Int) { }

    @Pointcut("execution(* toker.panel.service.PanelUserService.revokeAuthority(int)) &&" +
            "args(assignmentId)")
    fun authorityRevocation(assignmentId: Int) { }

    @Before("authorityAssignment(serverId, adminId, authorityId)")
    fun beforeAuthorityAssignment(serverId: Int, adminId: Int, authorityId: Int) {
        endSessions(adminId)
    }

    @Before("authorityRevocation(assignmentId)")
    fun beforeAuthorityRevocation(assignmentId: Int) {
        assignmentRepository.findById(assignmentId)
                .ifPresent { assignment: PanelUserAuthorityAssignment -> endSessions(assignment.panelUser!!.id!!) }
    }

    fun endSessions(adminId: Int) {
        val sessions = sessionRepository.findAll { root: Root<PanelUserSession?>, _, builder: CriteriaBuilder ->
            builder.and(
                    builder.equal(root.get(PanelUserSession_.user).get(PanelUser_.id), adminId),
                    builder.equal(root.get(PanelUserSession_.isEnded), false)
            )
        }
        sessions.forEach(Consumer { session: PanelUserSession ->
            session.isEnded = true
            EndedSessions.endSession(session.id!!)
        })
        sessionRepository.saveAll(sessions)
    }
}