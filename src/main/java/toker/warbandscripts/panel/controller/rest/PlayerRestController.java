package toker.warbandscripts.panel.controller.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import toker.warbandscripts.panel.entity.Player;
import toker.warbandscripts.panel.repository.PlayerRepository;

@RestController
public class PlayerRestController {

    @Autowired
    private PlayerRepository playerRepository;

    public PlayerRestController(PlayerRepository playerRepository) {
        this.playerRepository = playerRepository;
    }

    @GetMapping("/api/player")
    public Iterable<Player> players() {
        return playerRepository.findAll();
    }

    @GetMapping("/api/player/{id}")
    public Player player(@PathVariable int id) {
        return playerRepository.findById(id).orElse(null);
    }
}
