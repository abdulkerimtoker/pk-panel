package toker.warbandscripts.panel.controller.rest;

import com.fasterxml.jackson.annotation.JsonView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import toker.warbandscripts.panel.entity.Ban;
import toker.warbandscripts.panel.service.BanService;

import java.util.List;

@RestController
public class BanController {

    private BanService banService;

    public BanController(BanService banService) {
        this.banService = banService;
    }

    @GetMapping("/api/bans/player/{playerId}")
    @JsonView(Ban.View.PanelUser.class)
    public List<Ban> bansByPlayer(@PathVariable int playerId) {
        return banService.getBansOfPlayer(playerId);
    }

    @GetMapping("/api/bans/admin/{adminId}")
    @JsonView(Ban.View.None.class)
    public List<Ban> bansByAdmin(@PathVariable int adminId) {
        return banService.getBansOfAdmin(adminId);
    }

    @PostMapping("/api/ban")
    @JsonView(Ban.View.None.class)
    public Ban ban(@RequestBody Ban ban) {
        return banService.banPlayer(ban);
    }

    @PutMapping("/api/unban/{banId}")
    public void undoBan(@PathVariable int banId) {
        banService.undoBan(banId);
    }

    @PutMapping("/api/unban/player/{playerUniqueId}")
    public void unbanPlayer(@PathVariable int playerUniqueId) {
        banService.undoAllBansForPlayer(playerUniqueId);
    }
}
