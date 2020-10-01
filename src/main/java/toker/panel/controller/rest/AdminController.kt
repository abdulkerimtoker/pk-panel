package toker.panel.controller.rest

import com.fasterxml.jackson.annotation.JsonView
import org.springframework.data.crossstore.ChangeSetPersister
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.web.bind.annotation.*
import toker.panel.bean.CurrentUser
import toker.panel.bean.SelectedServerId
import toker.panel.entity.*
import toker.panel.repository.*
import toker.panel.service.PanelUserService
import java.util.*

@RestController
class AdminController(
        private val userRepository: PanelUserRepository,
        private val rankRepository: PanelUserRankRepository,
        private val authorityRepository: PanelUserAuthorityRepository,
        private val authorityAssignmentRepository: PanelUserAuthorityAssignmentRepository,
        private val adminInvitationRepository: AdminInvitationRepository,
        private val panelUserService: PanelUserService
) {

    @GetMapping("/api/admins")
    @JsonView(PanelUser.View.None::class)
    fun admins(): List<PanelUser> {
        return userRepository.findAll { root, query, builder ->
            root.fetch(PanelUser_.authorityAssignments)
            query.distinct(true)
            val userAssignmentJoin = root.join(PanelUser_.authorityAssignments)
            val assignmentAuthorityJoin = userAssignmentJoin.join(PanelUserAuthorityAssignment_.authority)
            builder.and(
                    builder.equal(userAssignmentJoin.get(PanelUserAuthorityAssignment_.server).get(Server_.id), SelectedServerId),
                    builder.equal(assignmentAuthorityJoin.get(PanelUserAuthority_.authorityName), "USER")
            )
        }
    }

    @GetMapping("/api/admins/{adminId}")
    @JsonView(PanelUser.View.None::class)
    fun admin(@PathVariable adminId: Int): PanelUser {
        return userRepository.findOne { root, query, builder ->
            root.fetch(PanelUser_.authorityAssignments)
            query.distinct(true)
            val userAssignmentJoin = root.join(PanelUser_.authorityAssignments)
            val assignmentAuthorityJoin = userAssignmentJoin.join(PanelUserAuthorityAssignment_.authority)
            builder.and(
                    builder.equal(root.get(PanelUser_.id), adminId),
                    builder.equal(userAssignmentJoin.get(PanelUserAuthorityAssignment_.server).get(Server_.id), SelectedServerId),
                    builder.equal(assignmentAuthorityJoin.get(PanelUserAuthority_.authorityName), "USER")
            )
        }.orElseThrow { ChangeSetPersister.NotFoundException() }
    }

    @GetMapping("/api/admins/{adminId}/authorities")
    @JsonView(PanelUserAuthorityAssignment.View.Authority::class)
    fun adminAuthorities(@PathVariable adminId: Int): List<PanelUserAuthorityAssignment> {
        return authorityAssignmentRepository.findAll { root, _, builder ->
            builder.and(
                    builder.equal(root.get(PanelUserAuthorityAssignment_.server).get(Server_.id), SelectedServerId),
                    builder.equal(root.get(PanelUserAuthorityAssignment_.panelUser).get(PanelUser_.id), adminId)
            )
        }
    }

    @PutMapping("/api/admins/{adminId}/authorities/{authorityId}")
    @PreAuthorize("hasRole(@authService.getRoleName('ADMIN_MANAGER'))")
    fun assignAuthority(@PathVariable adminId: Int, @PathVariable authorityId: Int) {
        panelUserService.assignAuthority(SelectedServerId, adminId, authorityId)
    }

    @DeleteMapping("/api/authorityAssignments/{assignmentId}")
    @PreAuthorize("hasRole(@authService.getRoleName('ADMIN_MANAGER'))")
    fun revokeAuthority(@PathVariable assignmentId: Int) {
        authorityAssignmentRepository.findById(assignmentId).ifPresent { assignment ->
            if (assignment.server!!.id == SelectedServerId)
                panelUserService.revokeAuthority(assignmentId)
        }
    }

    @GetMapping("/api/ranks")
    @JsonView(PanelUserRank.View.None::class)
    fun ranks(): List<PanelUserRank> = rankRepository.findAll()

    @GetMapping("/api/authorities")
    fun authorities(): List<PanelUserAuthority> = authorityRepository.findAll()

    @PostMapping("/api/admins/invite")
    @JsonView(AdminInvitation.View.None::class)
    fun invite(): AdminInvitation = panelUserService.createInvitation()

    @PutMapping("/api/invitation/{code}")
    fun useInvitation(@PathVariable code: String) = panelUserService.useInvitation(code)
}