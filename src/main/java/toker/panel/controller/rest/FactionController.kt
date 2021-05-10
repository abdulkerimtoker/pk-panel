package toker.panel.controller.rest

import com.fasterxml.jackson.annotation.JsonView
import org.springframework.cache.annotation.Cacheable
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController
import toker.panel.bean.SelectedServerId
import toker.panel.entity.Faction
import toker.panel.repository.FactionRepository
import toker.panel.repository.ServerRepository

@RestController
class FactionController(
    private val factionRepository: FactionRepository,
    private val serverRepository: ServerRepository
) {
    @GetMapping("/api/factions")
    @JsonView(Faction.View.None::class)
    fun factions(): List<Faction> {
        return factionRepository.findAllByServerId(SelectedServerId)
    }

    @PutMapping("/api/factions")
    fun saveFaction(@RequestBody faction: Faction) {
        faction.server = serverRepository.getOne(SelectedServerId)
        factionRepository.saveAndFlush(faction)
    }
}