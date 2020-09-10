package toker.warbandscripts.panel.repository;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import toker.warbandscripts.panel.entity.Chest;
import toker.warbandscripts.panel.entity.ChestSlot;
import toker.warbandscripts.panel.entity.Item;
import toker.warbandscripts.panel.entity.pk.ChestSlotPK;

import javax.transaction.Transactional;

public interface ChestSlotRepository extends BaseRepository<ChestSlot, ChestSlotPK> {

    @Modifying
    @Transactional
    @Query("UPDATE ChestSlot cs SET cs.item = :item, cs.ammo = :ammo " +
            "WHERE cs.chest = :chest AND cs.slot = :slot")
    int updateChestSlot(@Param("chest") Chest chest,
                        @Param("slot") Integer slot,
                        @Param("item") Item item,
                        @Param("ammo") Integer ammo);
}
