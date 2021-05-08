package toker.panel.repository

import org.springframework.data.jpa.repository.Modifying
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param
import org.springframework.transaction.annotation.Transactional
import toker.panel.entity.Inventory
import toker.panel.entity.InventorySlot
import toker.panel.entity.Item
import toker.panel.entity.pk.InventorySlotPK
import java.util.*

interface InventorySlotRepository : BaseRepository<InventorySlot, InventorySlotPK> {
    @Modifying
    @Transactional
    @Query("UPDATE InventorySlot SET item = :item, ammo = :ammo " +
            "WHERE inventory = :inventory AND slot = :slot")
    fun updateSlot(@Param("inventory") inventory: Inventory,
                   @Param("slot") slot: Int,
                   @Param("item") item: Item,
                   @Param("ammo") ammo: Int): Int
}