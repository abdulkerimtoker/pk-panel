package toker.warbandscripts.panel.controller.rest;

import com.fasterxml.jackson.annotation.JsonView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.web.bind.annotation.*;
import toker.warbandscripts.panel.entity.Profession;
import toker.warbandscripts.panel.entity.ProfessionAssignment;
import toker.warbandscripts.panel.service.ProfessionService;

import java.util.List;

@RestController
public class ProfessionController {

    private ProfessionService professionService;

    public ProfessionController(ProfessionService professionService) {
        this.professionService = professionService;
    }

    @GetMapping("/api/profession")
    @Cacheable("professions")
    public List<Profession> professions() {
        return professionService.getAllProfessions();
    }

    @PutMapping("/api/player/professionAssignment")
    @JsonView(ProfessionAssignment.View.Profession.class)
    public ProfessionAssignment saveProfessionAssignment(@RequestBody ProfessionAssignment professionAssignment) {
        return professionService.saveProfessionAssignment(professionAssignment);
    }

    @DeleteMapping("/api/player/{playerId}/profession/{professionId}")
    public void revokeProfession(@PathVariable int playerId, @PathVariable int professionId) {
        professionService.revokeProfession(playerId, professionId);
    }
}
