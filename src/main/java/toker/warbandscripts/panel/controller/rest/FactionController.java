package toker.warbandscripts.panel.controller.rest;

import com.fasterxml.jackson.annotation.JsonView;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import toker.warbandscripts.panel.bean.SelectedServerId;
import toker.warbandscripts.panel.entity.Faction;
import toker.warbandscripts.panel.repository.FactionRepository;

import java.util.List;

@RestController
public class FactionController {

    private FactionRepository factionRepository;

    public FactionController(FactionRepository factionRepository) {
        this.factionRepository = factionRepository;
    }

    @GetMapping("/api/faction")
    @Cacheable("factions")
    @JsonView(Faction.View.None.class)
    public List<Faction> factions() {
        return factionRepository.findAllByServerId(SelectedServerId.get());
    }
}
