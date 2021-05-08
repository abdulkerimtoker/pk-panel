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
import toker.panel.entity.InventorySlot
import toker.panel.entity.Log
import toker.panel.entity.Player
import toker.panel.entity.pk.InventorySlotPK
import toker.panel.repository.*

@Aspect
@Component
class PlayerLog(
    private val playerRepository: PlayerRepository,
    private val logRepository: LogRepository,
    private val serverRepository: ServerRepository,
    private val inventoryRepository: InventoryRepository,
    private val inventorySlotRepository: InventorySlotRepository
) {
    @Pointcut("execution(* toker.panel.repository.PlayerRepository+.save(..)) && args(player)")
    fun savePlayer(player: Player) {
    }

    @Pointcut("execution(* toker.panel.repository.PlayerRepository+.saveAndFlush(..)) && args(player)")
    fun saveAndFlushPlayer(player: Player) {
    }

    data class PlayerDifference(val playerId: Int, val old: Player, val new: Player)

    @Before("savePlayer(player) || saveAndFlushPlayer(player)")
    fun beforeSavePlayer(player: Player) {
        val current = playerRepository.findById(player.id!!).orElseGet { null }
        val writer = ObjectMapper().writerWithView(Player.View.FactionAndTroop::class.java)
        logRepository.saveAndFlush(Log(
            server = serverRepository.getOne(SelectedServerId),
            user = CurrentUser, type = Log.Type.PLAYER_UPDATE,
            data = writer.writeValueAsString(PlayerDifference(player.id!!, current, player))
        ))
    }

    @Pointcut("execution(* toker.panel.repository.InventorySlotRepository.saveAndFlush(..)) && args(slot)")
    fun saveInventorySlot(slot: InventorySlot) {
    }

    data class InventorySlotDifference(val playerId: Int, val slot: Int, val old: InventorySlot, val new: InventorySlot)

    @Before("saveInventorySlot(slot)")
    fun beforeSaveInventorySlot(slot: InventorySlot) {
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
}