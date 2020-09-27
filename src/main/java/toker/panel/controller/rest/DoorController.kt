package toker.panel.controller.rest

import com.fasterxml.jackson.annotation.JsonView
import org.springframework.data.crossstore.ChangeSetPersister
import org.springframework.web.bind.annotation.*
import toker.panel.bean.SelectedServerId
import toker.panel.entity.Door
import toker.panel.entity.DoorKey
import toker.panel.entity.pk.DoorPK
import toker.panel.service.DoorService

@RestController
class DoorController(private val doorService: DoorService) {
    @GetMapping("/api/door/{doorId}")
    @JsonView(DoorKey.View.Player::class)
    @Throws(ChangeSetPersister.NotFoundException::class)
    fun door(@PathVariable doorId: Int): Door {
        return doorService.getDoor(DoorPK(doorId, SelectedServerId))
                .orElseThrow { ChangeSetPersister.NotFoundException() }
    }

    @GetMapping("/api/door")
    @JsonView(Door.View::class)
    fun door(): List<Door> {
        return doorService.allDoors
    }

    @PutMapping("/api/player/doorKey")
    @JsonView(DoorKey.View.Door::class)
    fun assignDoorKey(@RequestBody doorKey: DoorKey?): DoorKey {
        return doorService.assignDoorKey(doorKey!!)
    }

    @DeleteMapping("/api/doorKey/{doorKeyId}")
    fun revokeDoorKey(@PathVariable doorKeyId: Int) {
        doorService.revokeDoorKey(doorKeyId)
    }
}