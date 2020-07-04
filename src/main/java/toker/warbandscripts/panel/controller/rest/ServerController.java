package toker.warbandscripts.panel.controller.rest;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import toker.warbandscripts.panel.authentication.JWTOpenIDAuthenticationToken;
import toker.warbandscripts.panel.entity.Server;
import toker.warbandscripts.panel.service.ServerService;

import java.util.List;
import java.util.stream.Collectors;

@RestController
public class ServerController {

    private ServerService serverService;

    public ServerController(ServerService serverService) {
        this.serverService = serverService;
    }

    @GetMapping("/api/servers")
    public List<Server> servers() {
        return serverService.getServersForAdmin();
    }
}
