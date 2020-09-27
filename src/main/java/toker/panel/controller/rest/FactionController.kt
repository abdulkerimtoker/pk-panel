package toker.panel.controller.rest

import com.fasterxml.jackson.annotation.JsonView
import org.springframework.cache.annotation.Cacheable
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController
import toker.panel.bean.SelectedServerId
import toker.panel.entity.Faction
import toker.panel.repository.FactionRepository

@RestController
class FactionController(private val factionRepository: FactionRepository) {
    @GetMapping("/api/faction")
    @Cacheable("factions")
    @JsonView(Faction.View.None::class)
    fun factions(): List<Faction> {
        return factionRepository.findAllByServerId(SelectedServerId)
    }
}