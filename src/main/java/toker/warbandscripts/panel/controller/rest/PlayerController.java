package toker.warbandscripts.panel.controller.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.web.bind.annotation.*;
import toker.warbandscripts.panel.annotation.FilterSpecification;
import toker.warbandscripts.panel.entity.Inventory;
import toker.warbandscripts.panel.entity.InventorySlot;
import toker.warbandscripts.panel.entity.Player;
import toker.warbandscripts.panel.repository.InventoryRepository;
import toker.warbandscripts.panel.repository.InventorySlotRepository;
import toker.warbandscripts.panel.repository.PlayerRepository;
import toker.warbandscripts.panel.service.PlayerService;

import javax.transaction.Transactional;
import javax.validation.Valid;
import java.util.List;

@RestController
public class PlayerController {

    @Autowired
    private PlayerService playerService;

    public PlayerController(PlayerService playerService) {
        this.playerService = playerService;
    }

    @GetMapping("/api/player/{playerId}")
    public Player player(@PathVariable int playerId) {
        return playerService.getPlayer(playerId).orElse(null);
    }

    @GetMapping("/api/player/search")
    public List<Player> search(@RequestParam String search) {
        return playerService.searchPlayers(search);
    }

    @PutMapping("/api/player/{playerId}/{field}")
    public boolean player(@PathVariable int playerId, @PathVariable String field,
                          @RequestBody Object value) {
        return playerService.setPlayerField(playerId, field, value);
    }

    @GetMapping("/api/player/{playerId}/inventory")
    public Inventory inventory(@PathVariable int playerId) {
        return playerService.getPlayerInventory(playerId);
    }

    @PutMapping("/api/player/inventory/{inventoryId}/slot")
    public InventorySlot inventorySlot(@PathVariable int inventoryId, @RequestBody InventorySlot inventorySlot) {
        return playerService.updatePlayerInventorySlot(inventoryId, inventorySlot);
    }

}
