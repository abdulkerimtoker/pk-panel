package toker.warbandscripts.panel.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import toker.warbandscripts.panel.entity.ServerConfiguration;
import toker.warbandscripts.panel.model.ServerModel;
import toker.warbandscripts.panel.repository.PlayerRepository;
import toker.warbandscripts.panel.repository.ServerRepository;
import toker.warbandscripts.panel.service.DedicatedServerService;

@Controller
@Secured("ROLE_SERVER_MANAGER")
public class ServerController {

    private DedicatedServerService serverService;
    private PlayerRepository playerRepository;
    private ServerRepository serverRepository;

    @Autowired
    public ServerController(DedicatedServerService serverService, PlayerRepository playerRepository, ServerRepository serverRepository) {
        this.serverService = serverService;
        this.playerRepository = playerRepository;
        this.serverRepository = serverRepository;
    }

    @GetMapping("/server")
    public String server(Model model) {
        ServerModel serverModel = new ServerModel();
        serverModel.setServerOn(serverService.isServerUp());
        serverModel.setFactions(playerRepository.getPlayerFactions());
        serverModel.setConfigurations(serverRepository.getServerConfigurations());
        serverModel.setAutoStart(serverService.isAutoStart());
        model.addAttribute("model", serverModel);
        model.addAttribute("updateConfigurationModel", new ServerConfiguration());
        return "server";
    }

    @PostMapping("/server/operation/{operation}")
    public String serverPost(@ModelAttribute("model") ServerModel model,
                             @PathVariable("operation") String operation) {
        model.setErrorOccured(false);
        if (operation.equals("start")) {
            model.setErrorOccured(!serverService.startServer());
        } else if (operation.equals("shutdown")) {
            model.setErrorOccured(!serverService.shutdownServer());
        } else if (operation.equals("toggleautostart")) {
            serverService.setAutoStart(!serverService.isAutoStart());
        }
        model.setServerOn(serverService.isServerUp());

        return "redirect:/server";
    }

    @PostMapping("/server/savefactions")
    public String saveFactions(@ModelAttribute("model") ServerModel model) {
        playerRepository.saveFactions(model.getFactions());
        return "redirect:";
    }

    @PostMapping("/server/configure")
    public String updateConfiguration(@ModelAttribute("updateConfigurationModel") ServerConfiguration serverConfiguration) {
        serverRepository.updateServerConfiguration(serverConfiguration);
        return "redirect:";
    }
}
