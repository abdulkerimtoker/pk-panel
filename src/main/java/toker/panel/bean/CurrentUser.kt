package toker.panel.bean

import org.springframework.beans.factory.getBean
import org.springframework.security.core.context.SecurityContextHolder
import toker.panel.authentication.JWTOpenIDAuthenticationToken
import toker.panel.entity.PanelUser
import toker.panel.repository.PanelUserRepository

val CurrentUser: PanelUser
    get() {
        val token = SecurityContextHolder.getContext().authentication
                as JWTOpenIDAuthenticationToken
        val repository = ApplicationContext
                .getBean<PanelUserRepository>(PanelUserRepository::class)
        return repository.findByClaimedIdentity(token.details as String)
    }