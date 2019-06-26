package toker.warbandscripts.panel.controller.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import toker.warbandscripts.panel.annotation.FilterSpecification;
import toker.warbandscripts.panel.entity.Player;
import toker.warbandscripts.panel.repository.PlayerRepository;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.List;
import java.util.Map;

@RestController
public class PlayerRestController {

    @Autowired
    private PlayerRepository playerRepository;

    public PlayerRestController(PlayerRepository playerRepository) {
        this.playerRepository = playerRepository;
    }

    @GetMapping("/api/player")
    public List<Player> players(@FilterSpecification Specification spec) {
        return playerRepository.findAll(spec);
    }

    @GetMapping("/api/player/{id}")
    public Player player(@PathVariable int id) {
        return playerRepository.findById(id).orElse(null);
    }
}
