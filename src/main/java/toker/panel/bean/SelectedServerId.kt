package toker.panel.bean

import org.springframework.security.core.context.SecurityContextHolder
import toker.panel.authentication.JWTOpenIDAuthenticationToken

val SelectedServerId: Int
    get() {
        val auth = SecurityContextHolder.getContext().authentication ?: return -1
        val token = auth as JWTOpenIDAuthenticationToken
        return token.selectedServerId
    }