package toker.panel.service

import com.fasterxml.jackson.databind.ObjectMapper
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.data.crossstore.ChangeSetPersister
import org.springframework.http.HttpStatus
import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.stereotype.Service
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.client.RestTemplate
import toker.panel.authentication.JWTOpenIDAuthenticationToken
import toker.panel.bean.CurrentUser
import toker.panel.bean.SelectedServerId
import toker.panel.entity.AdminInvitation
import toker.panel.entity.PanelUser
import toker.panel.entity.PanelUserAuthorityAssignment
import toker.panel.repository.*
import java.io.IOException
import java.sql.Timestamp
import java.time.Instant
import java.util.*
import java.util.regex.Pattern
import javax.persistence.OptimisticLockException
import javax.persistence.criteria.CriteriaBuilder
import javax.persistence.criteria.JoinType
import javax.persistence.criteria.Root

@Service
class PanelUserService(private val panelUserRepository: PanelUserRepository,
                       private val panelUserRankRepository: PanelUserRankRepository,
                       private val authorityRepository: PanelUserAuthorityRepository,
                       private val authorityAssignmentRepository: PanelUserAuthorityAssignmentRepository,
                       private val serverRepository: ServerRepository,
                       private val adminInvitationRepository: AdminInvitationRepository,
                       @field:Qualifier("steam-api") private val restTemplate: RestTemplate) {
    fun getOrCreateForClaimedIdentity(claimedIdentity: String?): PanelUser? {
        var panelUser = panelUserRepository.findOne { root: Root<PanelUser?>, _, builder: CriteriaBuilder ->
            root.fetch<Any, Any>("authorityAssignments", JoinType.LEFT)
            builder.equal(root.get<Any>("claimedIdentity"), claimedIdentity)
        }.orElse(null)
        if (panelUser == null) {
            val pattern = Pattern.compile("https://steamcommunity\\.com/openid/id/(?<steamid>\\d+)")
            val matcher = pattern.matcher(claimedIdentity)
            if (matcher.find()) {
                val steamId = matcher.group("steamid")
                val sea = restTemplate.getForObject(String.format("http://api.steampowered.com/ISteamUser/GetPlayerSummaries/v0002/?steamids=%s", steamId), String::class.java)
                val mapper = ObjectMapper()
                try {
                    val node = mapper.readTree(sea)
                    val username = node["response"]["players"]
                            .elements()
                            .next()["personaname"]
                            .asText()
                    panelUser = PanelUser()
                    panelUser.claimedIdentity = claimedIdentity
                    panelUser.username = username
                    panelUser.isLocked = false
                    panelUser.rank = panelUserRankRepository.getOne(1)
                    panelUser.creationTime = Timestamp.from(Instant.now())
                    panelUser = panelUserRepository.saveAndFlush<PanelUser>(panelUser)
                } catch (e: IOException) {
                    e.printStackTrace()
                }
            }
        }
        return panelUser
    }

    val authoritiesForCurrentUser: Collection<GrantedAuthority>
        get() {
            val token = SecurityContextHolder.getContext().authentication as JWTOpenIDAuthenticationToken
            return token.authorities
        }

    fun assignAuthority(serverId: Int, adminId: Int, authorityId: Int): PanelUserAuthorityAssignment {
        val assignment = PanelUserAuthorityAssignment(
                server =  serverRepository.getOne(serverId),
                panelUser = panelUserRepository.getOne(adminId),
                authority = authorityRepository.getOne(authorityId)
        )
        return authorityAssignmentRepository.saveAndFlush(assignment)
    }

    fun revokeAuthority(assignmentId: Int) {
        authorityAssignmentRepository.deleteById(assignmentId);
    }

    fun createInvitation(): AdminInvitation = adminInvitationRepository.saveAndFlush(AdminInvitation(
            code = UUID.randomUUID().toString().toUpperCase(),
            inviter = CurrentUser,
            server = serverRepository.getOne(SelectedServerId)
    ))

    @Throws(ChangeSetPersister.NotFoundException::class)
    fun useInvitation(code: String) {
        val invitation = adminInvitationRepository.findByCode(code)
                .orElseThrow { ChangeSetPersister.NotFoundException() }
        if (invitation.isUsed) throw ChangeSetPersister.NotFoundException()
        authorityAssignmentRepository.saveAndFlush(PanelUserAuthorityAssignment(
                panelUser = CurrentUser, server = invitation.server,
                authority = authorityRepository.findByAuthorityName("USER").orElseThrow()
        ))
        invitation.isUsed = true
        adminInvitationRepository.saveAndFlush(invitation)
    }

    @ExceptionHandler(ChangeSetPersister.NotFoundException::class)
    @ResponseStatus(value = HttpStatus.NOT_FOUND, reason = "Invalid key")
    fun invalidKey() { }
}