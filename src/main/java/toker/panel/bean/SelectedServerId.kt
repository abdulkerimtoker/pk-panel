package toker.panel.bean

import org.springframework.security.core.context.SecurityContextHolder
import toker.panel.authentication.GameAPIAuthentication
import toker.panel.authentication.JWTOpenIDAuthenticationToken
import toker.panel.entity.Server

val SelectedServerId: Int
    get() {
        val auth = SecurityContextHolder.getContext().authentication ?: return -1

        if (auth is JWTOpenIDAuthenticationToken) {
            return auth.selectedServerId
        }
        else if (auth is GameAPIAuthentication) {
            return (auth.principal as Server).id!!
        }

        return -1
    }