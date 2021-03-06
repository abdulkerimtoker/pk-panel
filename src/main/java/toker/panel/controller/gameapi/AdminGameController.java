package toker.panel.controller.gameapi;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import toker.panel.entity.Server;
import toker.panel.service.PlayerService;

@RestController
public class AdminGameController {

    private PlayerService playerService;

    public AdminGameController(PlayerService playerService) {
        this.playerService = playerService;
    }

    @GetMapping("/gameapi/adminhealplayer")
    public void treatPlayer(String patientname) {
        Server server = (Server) SecurityContextHolder.getContext()
                .getAuthentication().getPrincipal();
        playerService.getPlayer(patientname, server.getId()).ifPresent(player -> {
            player.setWoundTime(null);
            playerService.savePlayer(player);
        });
    }
}
