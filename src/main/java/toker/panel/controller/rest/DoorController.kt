package toker.panel.controller.rest

import com.fasterxml.jackson.annotation.JsonView
import org.springframework.data.crossstore.ChangeSetPersister
import org.springframework.web.bind.annotation.*
import toker.panel.bean.SelectedServerId
import toker.panel.entity.*
import toker.panel.entity.pk.DoorPK
import toker.panel.repository.DoorKeyRepository
import toker.panel.repository.DoorRepository
import toker.panel.service.DoorService
import toker.panel.service.PlayerService
import javax.persistence.criteria.CriteriaBuilder
import javax.persistence.criteria.Root

@RestController
class DoorController(
    private val doorService: DoorService,
    private val doorRepository: DoorRepository,
    private val doorKeyRepository: DoorKeyRepository,
    private val playerService: PlayerService
) {

    @GetMapping("/api/door/{doorId}")
    @JsonView(Door.View.None::class)
    @Throws(ChangeSetPersister.NotFoundException::class)
    fun door(@PathVariable doorId: Int): Door {
        return doorService.getDoor(DoorPK(doorId, SelectedServerId))
            .orElseThrow { ChangeSetPersister.NotFoundException() }
    }


    @GetMapping("/api/door/{index}/keys")
    @JsonView(DoorKey.View.Player::class)
    fun doorKeys(@PathVariable index: Int): List<DoorKey> =
        doorKeyRepository.findAll { root: Root<DoorKey>, _, builder: CriteriaBuilder ->
            val joinDoor = root.join(DoorKey_.door)
            val joinServer = joinDoor.join(Door_.server)
            builder.and(
                builder.equal(joinDoor.get(Door_.index), index),
                builder.equal(joinServer.get(Server_.id), SelectedServerId)
            )
        }

    @GetMapping("/api/door")
    @JsonView(Door.View.None::class)
    fun doors(): List<Door> {
        return doorService.allDoors
    }

    @PutMapping("/api/door/{index}")
    @JsonView(Door.View.None::class)
    fun door(@RequestBody door: Door, @PathVariable index: Int): Door {
        val newState = doorService.getDoor(DoorPK(index = index, server = SelectedServerId))
            .orElseThrow { ChangeSetPersister.NotFoundException() }
        newState.name = door.name
        newState.locked = door.locked
        return doorRepository.saveAndFlush(newState)
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