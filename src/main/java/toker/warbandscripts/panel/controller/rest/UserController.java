package toker.warbandscripts.panel.controller.rest;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import toker.warbandscripts.panel.service.PanelUserService;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@RestController
public class UserController {

    private PanelUserService panelUserService;

    public UserController(PanelUserService panelUserService) {
        this.panelUserService = panelUserService;
    }

    @GetMapping("/api/user/authorities")
    public List<String> authorities() {
        return panelUserService.getAuthoritiesForCurrentUser()
                .stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.toList());
    }
}
