package toker.warbandscripts.panel.controller.gameapi;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import toker.warbandscripts.panel.entity.Door;
import toker.warbandscripts.panel.entity.Player;
import toker.warbandscripts.panel.entity.Server;
import toker.warbandscripts.panel.entity.pk.DoorPK;
import toker.warbandscripts.panel.service.DoorService;
import toker.warbandscripts.panel.service.PlayerService;

@RestController
public class DoorGameController {

    private final DoorService doorService;
    private final PlayerService playerService;

    public DoorGameController(DoorService doorService,
                              PlayerService playerService) {
        this.doorService = doorService;
        this.playerService = playerService;
    }

    @GetMapping("/gameapi/checkprivatedoor")
    public String checkPrivateDoor(int id, int instanceid,
                                   int playerid, int left,
                                   int x, int y, int z) {
        Server server = (Server) SecurityContextHolder.getContext()
                .getAuthentication().getPrincipal();
        Door door = doorService.getDoor(new DoorPK(id, server.getId()))
                .orElse(null);
        if (door != null) {
            return String.format("%d|%d|%d|%d|%d|%d|%d|%d", id, instanceid,
                    playerid, door.getLocked() ? 1 : 0, left, x, y, z);
        }
        return String.format("%d|%d|%d|%d", id, instanceid,
                playerid, 1);
    }

    @GetMapping("/gameapi/lockunlockdoor")
    public String lockUnlockDoor(String playername, int playerid, int id,
                           @RequestParam(required = false) String admin) {
        Server server = (Server) SecurityContextHolder.getContext()
                .getAuthentication().getPrincipal();
        Door door = doorService.getDoor(new DoorPK(id, server.getId()))
                .orElse(null);
        Player player = playerService.getPlayer(playername, server.getId())
                .orElse(null);
        boolean able = playerService.getPlayerDoorKeys(player.getId())
                .stream()
                .anyMatch(doorKey -> doorKey.getDoor().equals(door));
        boolean isAdmin = admin != null;

        if (able) {
            doorService.changeLockState(id, server.getId(), !door.getLocked());
            return String.format("%d|%d|%d|%d", playerid, 1, id, !door.getLocked() ? 1 : 0);
        }
        else if (isAdmin) {
            doorService.changeLockState(id, server.getId(), !door.getLocked());
            return String.format("%d|%d|%d|%d", playerid, 0, id, !door.getLocked() ? 1 : 0);
        }

        return String.format("%d|%d|%d", playerid, 0, id);
    }
}
