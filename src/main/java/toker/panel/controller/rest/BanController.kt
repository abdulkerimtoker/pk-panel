package toker.panel.controller.rest

import com.fasterxml.jackson.annotation.JsonView
import org.springframework.data.crossstore.ChangeSetPersister
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.*
import org.springframework.web.server.ResponseStatusException
import toker.panel.entity.Ban
import toker.panel.service.BanService

@RestController
class BanController(private val banService: BanService) {
    @GetMapping("/api/players/{playerId}/bans")
    @JsonView(Ban.View.PanelUser::class)
    fun bansByPlayer(@PathVariable playerId: Int): List<Ban>? {
        return banService.getBansOfPlayer(playerId)
    }

    @GetMapping("/api/admins/{adminId}/bans")
    @JsonView(Ban.View.None::class)
    fun bansByAdmin(@PathVariable adminId: Int): List<Ban> {
        return banService.getBansOfAdmin(adminId)
    }

    @PostMapping("/api/bans")
    @JsonView(Ban.View.PanelUser::class)
    fun ban(@RequestBody ban: Ban): Ban {
        if (ban.minutes!! < 0) throw ResponseStatusException(HttpStatus.BAD_REQUEST)
        return banService.banPlayer(ban)
    }

    @PutMapping("/api/bans/{banId}/undo")
    @JsonView(Ban.View.None::class)
    @Throws(ChangeSetPersister.NotFoundException::class)
    fun undoBan(@PathVariable banId: Int): Ban {
        return banService.undoBan(banId)
    }

    @PutMapping("/api/bans/byUniqueId/{playerUniqueId}/undo")
    fun unbanPlayer(@PathVariable playerUniqueId: Int) {
        banService.undoAllBansForPlayer(playerUniqueId)
    }
}