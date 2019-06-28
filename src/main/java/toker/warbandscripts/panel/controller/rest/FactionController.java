package toker.warbandscripts.panel.controller.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import toker.warbandscripts.panel.entity.Faction;
import toker.warbandscripts.panel.repository.FactionRepository;

import java.util.List;

@RestController
public class FactionController {

    @Autowired
    private FactionRepository factionRepository;

    public FactionController(FactionRepository factionRepository) {
        this.factionRepository = factionRepository;
    }

    @GetMapping("/api/faction")
    public List<Faction> factions() {
        return factionRepository.findAll();
    }
}
