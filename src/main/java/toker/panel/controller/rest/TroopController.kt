package toker.panel.controller.rest

import org.springframework.cache.annotation.Cacheable
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController
import toker.panel.entity.Troop
import toker.panel.repository.TroopRepository

@RestController
class TroopController(private val troopRepository: TroopRepository) {
    @GetMapping("/api/troop")
    @Cacheable("troops")
    fun troops(): List<Troop> {
        return troopRepository.findAll()
    }
}