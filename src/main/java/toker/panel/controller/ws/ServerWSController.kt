package toker.panel.controller.ws;

import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.stereotype.Controller;
import toker.panel.bean.SelectedServerId;
import toker.panel.entity.Server;
import toker.panel.service.DedicatedServerService;
import toker.panel.service.ServerService;

import java.io.IOException;

@Controller
public class ServerWSController {

    private ServerService serverService;
    private DedicatedServerService dedicatedServerService;

    public ServerWSController(ServerService serverService,
                              DedicatedServerService dedicatedServerService) {
        this.serverService = serverService;
        this.dedicatedServerService = dedicatedServerService;
    }

    @MessageMapping("/start")
    public void start() throws ChangeSetPersister.NotFoundException, IOException {
        int serverId = SelectedServerId.get();
        Server server = serverService.getServer(serverId, "startupCommands");
        dedicatedServerService.startServer(server);
    }

    @MessageMapping("/shutdown")
    public void shutdown() throws ChangeSetPersister.NotFoundException {
        dedicatedServerService.shutdownServer(
                serverService.getServer(SelectedServerId.get()));
    }
}
