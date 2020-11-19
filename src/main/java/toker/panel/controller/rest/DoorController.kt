package toker.panel.controller.rest

import com.fasterxml.jackson.annotation.JsonView
import org.springframework.data.crossstore.ChangeSetPersister
import org.springframework.web.bind.annotation.*
import toker.panel.bean.SelectedServerId
import toker.panel.entity.Door
import toker.panel.entity.DoorKey
import toker.panel.entity.DoorKey_
import toker.panel.entity.Door_
import toker.panel.entity.pk.DoorPK
import toker.panel.repository.DoorKeyRepository
import toker.panel.repository.DoorRepository
import toker.panel.service.DoorService
import javax.persistence.criteria.CriteriaBuilder
import javax.persistence.criteria.Root

@RestController
class DoorController(private val doorService: DoorService,
                     private val doorRepository: DoorRepository,
                     private val doorKeyRepository: DoorKeyRepository) {

    @GetMapping("/api/door/{doorId}")
    @JsonView(Door.View.None::class)
    @Throws(ChangeSetPersister.NotFoundException::class)
    fun door(@PathVariable doorId: Int): Door {
        return doorService.getDoor(DoorPK(doorId, SelectedServerId))
                .orElseThrow { ChangeSetPersister.NotFoundException() }
    }


    @GetMapping("/api/door/{index}/keys")
    @JsonView(DoorKey.View.Player::class)
    fun doorKeys(@PathVariable index: Int): List<DoorKey> = doorKeyRepository.findAll { 
        root: Root<DoorKey>, _, builder: CriteriaBuilder ->
        builder.equal(root.get(DoorKey_.door).get(Door_.index), index)
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