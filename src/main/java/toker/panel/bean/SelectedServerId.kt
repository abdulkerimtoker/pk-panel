package toker.panel.bean

import org.springframework.security.core.context.SecurityContextHolder
import toker.panel.authentication.JWTOpenIDAuthenticationToken

val SelectedServerId: Int
    get() {
        val token = SecurityContextHolder
                .getContext().authentication as JWTOpenIDAuthenticationToken
        return token.selectedServerId
    }