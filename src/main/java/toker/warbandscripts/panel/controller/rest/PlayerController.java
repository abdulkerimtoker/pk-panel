package toker.warbandscripts.panel.controller.rest;

import com.fasterxml.jackson.annotation.JsonView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import toker.warbandscripts.panel.annotation.FilterSpecification;
import toker.warbandscripts.panel.entity.DoorKey;
import toker.warbandscripts.panel.entity.Inventory;
import toker.warbandscripts.panel.entity.InventorySlot;
import toker.warbandscripts.panel.entity.Player;
import toker.warbandscripts.panel.repository.InventoryRepository;
import toker.warbandscripts.panel.repository.InventorySlotRepository;
import toker.warbandscripts.panel.repository.PlayerRepository;
import toker.warbandscripts.panel.service.PlayerService;

import javax.persistence.OptimisticLockException;
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
    public Player player(@PathVariable int playerId) throws ChangeSetPersister.NotFoundException {
        return playerService.getPlayer(playerId)
                .orElseThrow(ChangeSetPersister.NotFoundException::new);
    }

    @GetMapping("/api/player/search")
    public List<Player> search(@RequestParam String search) {
        return playerService.searchPlayers(search);
    }

    @PutMapping("/api/player")
    public Player updatePlayer(@RequestBody Player player) {
        return playerService.savePlayer(player);
    }

    @ExceptionHandler(OptimisticLockException.class)
    @ResponseStatus(value = HttpStatus.CONFLICT, reason = "Wrong version")
    public void versionConflict() {}

    @PutMapping("/api/player/{playerId}/{field}")
    public boolean updatePlayerField(@PathVariable int playerId, @PathVariable String field,
                          @RequestBody Object value) {
        return playerService.setPlayerField(playerId, field, value);
    }

    @GetMapping("/api/player/{playerId}/inventory")
    public Inventory inventory(@PathVariable int playerId) {
        return playerService.getPlayerInventory(playerId);
    }

    @PutMapping("/api/player/inventory/{inventoryId}/slot")
    public boolean inventorySlot(@PathVariable int inventoryId, @RequestBody InventorySlot inventorySlot) {
        return playerService.updatePlayerInventorySlot(inventoryId, inventorySlot);
    }

    @GetMapping("/api/player/{playerId}/doorKeys")
    @JsonView(DoorKey.View.Door.class)
    public List<DoorKey> doorKeys(@PathVariable int playerId) {
        return playerService.getPlayerDoorKeys(playerId);
    }
}
