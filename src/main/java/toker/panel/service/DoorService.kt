package toker.panel.service

import org.springframework.stereotype.Service
import toker.panel.bean.SelectedServerId
import toker.panel.entity.Door
import toker.panel.entity.DoorKey
import toker.panel.entity.pk.DoorPK
import toker.panel.repository.DoorKeyRepository
import toker.panel.repository.DoorRepository
import toker.panel.repository.PlayerRepository
import java.util.*

@Service
class DoorService(private val doorRepository: DoorRepository,
                  private val doorKeyRepository: DoorKeyRepository,
                  private val playerRepository: PlayerRepository) {
    fun getDoor(doorId: DoorPK): Optional<Door> {
        return doorRepository.findById(doorId)
    }

    val allDoors: List<Door>
        get() = doorRepository.findAllByServerId(SelectedServerId)

    fun assignDoorKey(doorKey: DoorKey): DoorKey {
        return doorKeyRepository.saveAndFlush(doorKey)
    }

    fun revokeDoorKey(doorKeyId: Int) {
        doorKeyRepository.deleteById(doorKeyId)
    }

    fun changeLockState(index: Int, serverId: Int, locked: Boolean): Boolean {
        return doorRepository.changeLockState(index, serverId, locked) > 0
    }
}