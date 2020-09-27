package toker.panel.controller.rest

import com.fasterxml.jackson.annotation.JsonView
import org.springframework.cache.annotation.Cacheable
import org.springframework.web.bind.annotation.*
import toker.panel.entity.Profession
import toker.panel.entity.ProfessionAssignment
import toker.panel.service.ProfessionService

@RestController
class ProfessionController(private val professionService: ProfessionService) {
    @GetMapping("/api/profession")
    @Cacheable("professions")
    fun professions(): List<Profession> {
        return professionService.allProfessions
    }

    @PutMapping("/api/player/professionAssignment")
    @JsonView(ProfessionAssignment.View.Profession::class)
    fun saveProfessionAssignment(@RequestBody professionAssignment: ProfessionAssignment): ProfessionAssignment {
        return professionService.saveProfessionAssignment(professionAssignment)
    }

    @DeleteMapping("/api/player/{playerId}/profession/{professionId}")
    fun revokeProfession(@PathVariable playerId: Int, @PathVariable professionId: Int) {
        professionService.revokeProfession(playerId, professionId)
    }
}