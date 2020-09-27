package toker.panel.controller

import com.auth0.jwt.JWT
import com.auth0.jwt.algorithms.Algorithm
import org.springframework.http.HttpHeaders
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.security.openid.OpenIDAuthenticationStatus
import org.springframework.security.openid.OpenIDConsumer
import org.springframework.security.openid.OpenIDConsumerException
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import toker.panel.entity.PanelUserAuthorityAssignment
import toker.panel.entity.PanelUserSession
import toker.panel.repository.PanelUserSessionRepository
import toker.panel.service.PanelUserService
import java.net.MalformedURLException
import java.net.URL
import java.util.*
import java.util.stream.Collectors
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletRequestWrapper

@RestController
class OpenIDLoginController(private val consumer: OpenIDConsumer,
                            private val panelUserService: PanelUserService,
                            private val sessionRepo: PanelUserSessionRepository) {
    @RequestMapping("/api/login")
    @Throws(OpenIDConsumerException::class)
    fun login(request: HttpServletRequest): ResponseEntity<*> {
        val requestUrl = request.requestURL.toString()
        val redirectTo = consumer.beginConsumption(request,
                OPENID_CLAIMED_IDENTITY,
                requestUrl.replace(request.servletPath, "/processLogin"),
                lookupRealm(requestUrl))
        return ResponseEntity.status(HttpStatus.OK)
                .header(HttpHeaders.LOCATION, redirectTo)
                .build<Any>()
    }

    @RequestMapping("/api/processLogin")
    @Throws(OpenIDConsumerException::class)
    fun processLogin(request: HttpServletRequest): ResponseEntity<*> {
        val token = consumer.endConsumption(object : HttpServletRequestWrapper(request) {
            override fun getRequestURL(): StringBuffer {
                return StringBuffer(request.requestURL.toString()
                        .replace("api/processLogin", "processLogin"))
            }
        })
        request.session.invalidate()
        if (token.status == OpenIDAuthenticationStatus.SUCCESS) {
            val panelUser = panelUserService.getOrCreateForClaimedIdentity(token.identityUrl)
            if (panelUser != null) {
                var session = PanelUserSession()
                session.user = panelUser
                session = sessionRepo.save(session)
                val authorities: Collection<PanelUserAuthorityAssignment>? = panelUser.authorityAssignments
                val authorizations = if (authorities != null) panelUser.authorityAssignments!!
                        .stream()
                        .map { (_, _, server, authority) ->
                            String.format(
                                    "ROLE_%d_%s",
                                    server!!.id,
                                    authority!!.authorityName
                            )
                        }
                        .collect(Collectors.toList()) else LinkedList()
                val jwt = JWT.create()
                        .withSubject(panelUser.username)
                        .withClaim("Identity", token.identityUrl)
                        .withClaim("Session-ID", session.id)
                        .withClaim("Authorizations", authorizations)
                        .sign(Algorithm.HMAC512("sea".toByteArray()))
                return ResponseEntity.status(HttpStatus.ACCEPTED)
                        .header(HttpHeaders.AUTHORIZATION, "Bearer $jwt")
                        .build<Any>()
            }
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build<Any>()
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).build<Any>()
    }

    private fun lookupRealm(requestUrl: String): String? {
        return try {
            val url = URL(requestUrl)
            val port = url.port
            val realmBuffer = StringBuilder()
                    .append(url.protocol).append("://").append(url.host)
            if (port > 0) {
                realmBuffer.append(":").append(port)
            }
            realmBuffer.append("/")
            realmBuffer.toString()
        } catch (e: MalformedURLException) {
            e.printStackTrace()
            null
        }
    }

    companion object {
        private const val OPENID_CLAIMED_IDENTITY = "https://steamcommunity.com/openid"
    }
}