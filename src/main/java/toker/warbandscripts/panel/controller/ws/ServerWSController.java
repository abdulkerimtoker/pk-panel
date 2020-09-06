package toker.warbandscripts.panel.controller.ws;

import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.messaging.simp.annotation.SubscribeMapping;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import toker.warbandscripts.panel.bean.SelectedServerId;
import toker.warbandscripts.panel.entity.Server;
import toker.warbandscripts.panel.service.DedicatedServerService;
import toker.warbandscripts.panel.service.ServerService;

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
