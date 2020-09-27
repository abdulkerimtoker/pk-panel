package toker.panel.service

import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.stereotype.Service
import toker.panel.authentication.JWTOpenIDAuthenticationToken
import toker.panel.entity.Server
import toker.panel.repository.PlayerRepository
import toker.panel.repository.ServerRepository
import java.lang.reflect.Method
import java.util.*
import java.util.stream.Collectors

@Service("authService")
class AuthService(private val serverRepository: ServerRepository,
                  private val playerRepository: PlayerRepository) {

    fun canModifyPlayer(playerId: Int): Boolean {
        val faction = playerRepository.findById(playerId).orElse(null).faction ?: return true
        val token = SecurityContextHolder.getContext().authentication as JWTOpenIDAuthenticationToken
        return token.authorities
                .stream()
                .map { obj: GrantedAuthority -> obj.authority }
                .collect(Collectors.toList())
                .contains(String.format("ROLE_%d_PLAYER_MANAGER", faction.server?.id))
    }

    val serversForAdmin: List<Server>
        get() {
            val token = SecurityContextHolder.getContext().authentication as JWTOpenIDAuthenticationToken
            val authorities = token.authorities
                    .stream()
                    .map { obj: GrantedAuthority -> obj.authority }
                    .collect(Collectors.toList())
            return serverRepository.findAll()
                    .stream()
                    .filter { (id) -> authorities.contains(String.format("ROLE_%d_USER", id)) }
                    .collect(Collectors.toList())
        }
}