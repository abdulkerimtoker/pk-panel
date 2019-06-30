package toker.warbandscripts.panel.controller.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.web.bind.annotation.*;
import toker.warbandscripts.panel.annotation.FilterSpecification;
import toker.warbandscripts.panel.entity.Inventory;
import toker.warbandscripts.panel.entity.Player;
import toker.warbandscripts.panel.repository.InventoryRepository;
import toker.warbandscripts.panel.repository.PlayerRepository;

import javax.validation.Valid;
import java.util.List;

@RestController
public class PlayerRestController {

    @Autowired
    private PlayerRepository playerRepository;

    @Autowired
    private InventoryRepository inventoryRepository;

    public PlayerRestController(PlayerRepository playerRepository, InventoryRepository inventoryRepository) {
        this.playerRepository = playerRepository;
        this.inventoryRepository = inventoryRepository;
    }

    @GetMapping("/api/player/{id}")
    public Player player(@PathVariable int id) {
        return playerRepository.findById(id).orElse(null);
    }

    @GetMapping("/api/player/search")
    public List<Player> search(@RequestParam String search) {
        return playerRepository.likeSearch(search);
    }

    @PutMapping("/api/player")
    public Player player(@RequestBody Player player) {
        return playerRepository.save(player);
    }

    @GetMapping("/api/player/{id}/inventory")
    public Inventory inventory(@PathVariable int id) {
        return this.inventoryRepository.findFirstByPlayerId(id);
    }

}
