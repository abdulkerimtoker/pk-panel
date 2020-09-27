package toker.panel.controller.rest

import org.springframework.security.core.GrantedAuthority
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController
import toker.panel.service.PanelUserService
import java.util.stream.Collectors

@RestController
class UserController(private val panelUserService: PanelUserService) {
    @GetMapping("/api/user/authorities")
    fun authorities(): List<String> {
        return panelUserService.authoritiesForCurrentUser
                .stream()
                .map { obj: GrantedAuthority -> obj.authority }
                .collect(Collectors.toList())
    }
}