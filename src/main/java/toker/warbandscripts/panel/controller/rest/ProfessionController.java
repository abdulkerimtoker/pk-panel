package toker.warbandscripts.panel.controller.rest;

import com.fasterxml.jackson.annotation.JsonView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import toker.warbandscripts.panel.entity.Profession;
import toker.warbandscripts.panel.entity.ProfessionAssignment;
import toker.warbandscripts.panel.service.ProfessionService;

import java.util.List;

@RestController
public class ProfessionController {

    @Autowired
    private ProfessionService professionService;

    public ProfessionController(ProfessionService professionService) {
        this.professionService = professionService;
    }

    @GetMapping("/api/profession")
    public List<Profession> professions() {
        return professionService.getAllProfessions();
    }

    @PutMapping("/api/player/professionAssignment")
    @JsonView(ProfessionAssignment.View.Profession.class)
    public ProfessionAssignment saveProfessionAssignment(@RequestBody ProfessionAssignment professionAssignment) {

        return professionService.saveProfessionAssignment(professionAssignment);
    }
}
