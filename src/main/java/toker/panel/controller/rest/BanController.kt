package toker.panel.controller.rest;

import com.fasterxml.jackson.annotation.JsonView;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import toker.panel.entity.Ban;
import toker.panel.service.BanService;

import java.util.List;

@RestController
public class BanController {

    private BanService banService;

    public BanController(BanService banService) {
        this.banService = banService;
    }

    @GetMapping("/api/players/{playerId}/bans")
    @JsonView(Ban.View.PanelUser.class)
    public List<Ban> bansByPlayer(@PathVariable int playerId) {
        return banService.getBansOfPlayer(playerId);
    }

    @GetMapping("/api/admins/{adminId}/bans")
    @JsonView(Ban.View.None.class)
    public List<Ban> bansByAdmin(@PathVariable int adminId) {
        return banService.getBansOfAdmin(adminId);
    }

    @PostMapping("/api/bans")
    @JsonView(Ban.View.PanelUser.class)
    public Ban ban(@RequestBody Ban ban) {
        if (ban.getMinutes() < 0)
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        return banService.banPlayer(ban);
    }

    @PutMapping("/api/bans/{banId}/undo")
    @JsonView(Ban.View.None.class)
    public Ban undoBan(@PathVariable int banId) throws ChangeSetPersister.NotFoundException {
        return banService.undoBan(banId);
    }

    @PutMapping("/api/bans/byUniqueId/{playerUniqueId}/undo")
    public void unbanPlayer(@PathVariable int playerUniqueId) {
        banService.undoAllBansForPlayer(playerUniqueId);
    }
}
