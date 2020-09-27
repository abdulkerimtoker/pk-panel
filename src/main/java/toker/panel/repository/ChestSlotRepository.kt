package toker.panel.repository

import org.springframework.data.jpa.repository.Modifying
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param
import toker.panel.entity.Chest
import toker.panel.entity.ChestSlot
import toker.panel.entity.Item
import toker.panel.entity.pk.ChestSlotPK
import javax.transaction.Transactional

interface ChestSlotRepository : BaseRepository<ChestSlot, ChestSlotPK> {
    @Modifying
    @Transactional
    @Query("UPDATE ChestSlot cs SET cs.item = :item, cs.ammo = :ammo " +
            "WHERE cs.chest = :chest AND cs.slot = :slot")
    fun updateChestSlot(@Param("chest") chest: Chest,
                        @Param("slot") slot: Int,
                        @Param("item") item: Item,
                        @Param("ammo") ammo: Int): Int
}