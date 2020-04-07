package toker.warbandscripts.panel.controller.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import toker.warbandscripts.panel.entity.Troop;
import toker.warbandscripts.panel.repository.TroopRepository;

import java.util.List;

@RestController
public class TroopController {

    private TroopRepository troopRepository;

    public TroopController(TroopRepository troopRepository) {
        this.troopRepository = troopRepository;
    }

    @GetMapping("/api/troop")
    @Cacheable("troops")
    public List<Troop> troops() {
        return troopRepository.findAll();
    }
}
