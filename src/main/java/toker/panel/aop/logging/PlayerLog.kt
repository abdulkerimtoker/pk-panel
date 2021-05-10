package toker.panel.aop.logging

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.databind.ser.FilterProvider
import com.fasterxml.jackson.databind.ser.impl.SimpleBeanPropertyFilter
import com.fasterxml.jackson.databind.ser.impl.SimpleFilterProvider
import org.aspectj.lang.annotation.Aspect
import org.aspectj.lang.annotation.Before
import org.aspectj.lang.annotation.Pointcut
import org.springframework.stereotype.Component
import toker.panel.bean.CurrentUser
import toker.panel.bean.SelectedServerId
import toker.panel.entity.*
import toker.panel.entity.pk.InventorySlotPK
import toker.panel.entity.pk.LanguageProficiencyPK
import toker.panel.repository.*

@Aspect
@Component
class PlayerLog(
    private val playerRepository: PlayerRepository,
    private val logRepository: LogRepository,
    private val serverRepository: ServerRepository,
    private val inventoryRepository: InventoryRepository,
    private val inventorySlotRepository: InventorySlotRepository,
    private val doorKeyRepository: DoorKeyRepository
) {
    @Pointcut("execution(* toker.panel.repository.PlayerRepository+.save(..)) && args(player)")
    fun savePlayer(player: Player) { }

    @Pointcut("execution(* toker.panel.repository.PlayerRepository+.saveAndFlush(..)) && args(player)")
    fun saveAndFlushPlayer(player: Player) { }

    data class PlayerDifference(val playerId: Int, val old: Player, val new: Player)

    @Before("savePlayer(player) || saveAndFlushPlayer(player)")
    fun beforeSavePlayer(player: Player) {
        if (CurrentUser == null) return

        val current = playerRepository.findById(player.id!!).orElseGet { null }
        val writer = ObjectMapper().writerWithView(Player.View.FactionAndTroop::class.java)
        logRepository.saveAndFlush(Log(
            server = serverRepository.getOne(SelectedServerId),
            user = CurrentUser, type = Log.Type.PLAYER_UPDATE,
            data = writer.writeValueAsString(PlayerDifference(player.id!!, current, player))
        ))
    }

    @Pointcut("execution(* toker.panel.repository.InventorySlotRepository+.save(..)) && args(slot)")
    fun saveInventorySlot(slot: InventorySlot) { }

    @Pointcut("execution(* toker.panel.repository.InventorySlotRepository+.saveAndFlush(..)) && args(slot)")
    fun saveAndFlushInventorySlot(slot: InventorySlot) { }

    data class InventorySlotDifference(val playerId: Int, val slot: Int, val old: InventorySlot, val new: InventorySlot)

    @Before("saveInventorySlot(slot) || saveAndFlushInventorySlot(slot)")
    fun beforeSaveInventorySlot(slot: InventorySlot) {
        if (CurrentUser == null) return

        val current = inventorySlotRepository.findById(InventorySlotPK(slot = slot.slot, inventory = slot.inventory!!.id)).orElseGet { null }
        val inventory = inventoryRepository.findById(slot.inventory!!.id!!).get()
        val player = inventory.player!!
        val writer = ObjectMapper().writerWithView(InventorySlot.View.None::class.java)
        logRepository.saveAndFlush(Log(
            server = serverRepository.getOne(SelectedServerId),
            user = CurrentUser, type = Log.Type.INVENTORY_SLOT_UPDATE,
            data = writer.writeValueAsString(InventorySlotDifference(player.id!!, slot.slot!!, current, slot))
        ))
    }

    @Pointcut("execution(* toker.panel.repository.NoticeBoardAccessRepository+.save(..)) && args(access)")
    fun saveBoardAccess(access: NoticeBoardAccess) { }

    @Pointcut("execution(* toker.panel.repository.NoticeBoardAccessRepository+.saveAndFlush(..)) && args(access)")
    fun saveAndFlushBoardAccess(access: NoticeBoardAccess) { }

    data class BoardAccessGrant(val playerId: Int, val boardIndex: Int)

    @Before("saveBoardAccess(access) || saveAndFlushBoardAccess(access)")
    fun beforeSaveBoardAccess(access: NoticeBoardAccess) {
        val grant = BoardAccessGrant(access.player!!.id!!, access.board!!.index!!)
        val writer = ObjectMapper().writer()
        logRepository.saveAndFlush(Log(
            server = serverRepository.getOne(SelectedServerId),
            user = CurrentUser, type = Log.Type.BOARD_ACCESS_GRANT,
            data = writer.writeValueAsString(grant)
        ))
    }

    @Pointcut("execution(* toker.panel.repository.LanguageProficiencyRepository+.save(..)) && args(proficiency)")
    fun saveLanguageProficiency(proficiency: LanguageProficiency) { }

    @Pointcut("execution(* toker.panel.repository.LanguageProficiencyRepository+.saveAndFlush(..)) && args(proficiency)")
    fun saveAndFlushLanguageProficiency(proficiency: LanguageProficiency) { }

    data class LanguageProficiencyGrant(val playerId: Int, val languageId: Int)

    @Before("saveLanguageProficiency(proficiency) || saveAndFlushLanguageProficiency(proficiency)")
    fun beforeSaveSaveLanguageProficiency(proficiency: LanguageProficiency) {
        if (CurrentUser == null) return

        val grant = LanguageProficiencyGrant(proficiency.player!!.id!!, proficiency.language!!.id!!)
        val writer = ObjectMapper().writer()
        logRepository.saveAndFlush(Log(
            server = serverRepository.getOne(SelectedServerId),
            user = CurrentUser, type = Log.Type.LANGUAGE_PROFICIENCY_GRANT,
            data = writer.writeValueAsString(grant)
        ))
    }

    @Pointcut("execution(* toker.panel.repository.LanguageProficiencyRepository+.deleteById(..)) && args(proficiencyPK)")
    fun deleteLanguageProficiency(proficiencyPK: LanguageProficiencyPK) { }

    data class LanguageProficiencyRevoke(val playerId: Int, val languageId: Int)

    @Before("deleteLanguageProficiency(proficiencyPK)")
    fun beforeDeleteLanguageProficiency(proficiencyPK: LanguageProficiencyPK) {
        val revoke = LanguageProficiencyRevoke(proficiencyPK.player!!, proficiencyPK.language!!)
        val writer = ObjectMapper().writer()
        logRepository.saveAndFlush(Log(
            server = serverRepository.getOne(SelectedServerId),
            user = CurrentUser, type = Log.Type.LANGUAGE_PROFICIENCY_REVOKE,
            data = writer.writeValueAsString(revoke)
        ))
    }

    @Pointcut("execution(* toker.panel.repository.DoorKeyRepository+.save(..)) && args(doorKey)")
    fun saveDoorKey(doorKey: DoorKey) { }

    @Pointcut("execution(* toker.panel.repository.DoorKeyRepository+.saveAndFlush(..)) && args(doorKey)")
    fun saveAndFlushDoorKey(doorKey: DoorKey) { }

    data class DoorKeyGrant(val key: DoorKey)

    @Before("saveDoorKey(doorKey) || saveAndFlushDoorKey(doorKey)")
    fun beforeSaveDoorKey(doorKey: DoorKey) {
        if (CurrentUser == null) return

        val grant = DoorKeyGrant(doorKey)
        val writer = ObjectMapper().writerWithView(DoorKey.View.PlayerAndDoor::class.java)
        logRepository.saveAndFlush(Log(
            server = serverRepository.getOne(SelectedServerId),
            user = CurrentUser, type = Log.Type.DOOR_KEY_GRANT,
            data = writer.writeValueAsString(grant)
        ))
    }

    @Pointcut("execution(* toker.panel.repository.DoorKeyRepository+.deleteById(..)) && args(doorKeyId)")
    fun deleteDoorKey(doorKeyId: Int?) { }

    data class DoorKeyRevoke(val key: DoorKey)

    @Before("deleteDoorKey(doorKeyId)")
    fun beforeDeleteDoorKey(doorKeyId: Int?) {
        val key = doorKeyRepository.findById(doorKeyId!!).orElseGet { null }
        val revoke = DoorKeyRevoke(key)
        val writer = ObjectMapper().writerWithView(DoorKey.View.PlayerAndDoor::class.java)
        logRepository.saveAndFlush(Log(
            server = serverRepository.getOne(SelectedServerId),
            user = CurrentUser, type = Log.Type.DOOR_KEY_REVOKE,
            data = writer.writeValueAsString(revoke)
        ))
    }
}