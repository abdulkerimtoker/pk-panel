package toker.warbandscripts.panel.controller.rest;

import com.fasterxml.jackson.annotation.JsonView;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import toker.warbandscripts.panel.bean.SelectedServerId;
import toker.warbandscripts.panel.entity.Server;
import toker.warbandscripts.panel.service.AuthService;
import toker.warbandscripts.panel.service.ServerService;

import java.io.File;
import java.io.IOException;
import java.util.List;

@RestController
public class ServerController {

    private AuthService authService;
    private ServerService serverService;

    public ServerController(AuthService authService,
                            ServerService serverService) {
        this.authService = authService;
        this.serverService = serverService;
    }

    @GetMapping("/api/servers")
    @JsonView(Server.View.None.class)
    public List<Server> servers() {
        return authService.getServersForAdmin();
    }

    @GetMapping("/api/servers/{serverId}")
    @JsonView(Server.View.StartupCommands.class)
    public Server server(@PathVariable int serverId) throws ChangeSetPersister.NotFoundException {
        return serverService.getServer(serverId, "startupCommands");
    }

    @PostMapping("/api/uploadMap")
    public void uploadMap(@RequestParam MultipartFile map)
            throws ChangeSetPersister.NotFoundException, IOException {
        Server server = serverService.getServer(SelectedServerId.get());
        map.transferTo(new File(serverService.getMapDir(server), map.getOriginalFilename()));
    }
}
