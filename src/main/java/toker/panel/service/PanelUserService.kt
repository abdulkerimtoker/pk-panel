package toker.panel.service

import com.fasterxml.jackson.databind.ObjectMapper
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.stereotype.Service
import org.springframework.web.client.RestTemplate
import toker.panel.authentication.JWTOpenIDAuthenticationToken
import toker.panel.entity.PanelUser
import toker.panel.repository.PanelUserRankRepository
import toker.panel.repository.PanelUserRepository
import java.io.IOException
import java.sql.Timestamp
import java.time.Instant
import java.util.regex.Pattern
import javax.persistence.criteria.CriteriaBuilder
import javax.persistence.criteria.CriteriaQuery
import javax.persistence.criteria.JoinType
import javax.persistence.criteria.Root

@Service
class PanelUserService(private val panelUserRepository: PanelUserRepository,
                       private val panelUserRankRepository: PanelUserRankRepository,
                       @field:Qualifier("steam-api") private val restTemplate: RestTemplate) {
    fun getOrCreateForClaimedIdentity(claimedIdentity: String?): PanelUser? {
        var panelUser = panelUserRepository.findOne { root: Root<PanelUser?>, query: CriteriaQuery<*>?, builder: CriteriaBuilder ->
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
}