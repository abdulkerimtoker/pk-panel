package toker.warbandscripts.panel.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;
import toker.warbandscripts.panel.entity.Inventory;
import toker.warbandscripts.panel.entity.InventorySlot;
import toker.warbandscripts.panel.entity.Item;

import java.util.Optional;

public interface InventorySlotRepository extends BaseRepository<InventorySlot, Integer> {

    Optional<InventorySlot> findByInventoryIdAndSlot(Integer inventoryId, Integer slot);

    @Modifying
    @Transactional
    @Query("UPDATE InventorySlot SET item = :item, ammo = :ammo " +
            "WHERE inventory = :inventory AND slot = :slot")
    int updateSlot(@Param("inventory") Inventory inventory,
                   @Param("slot") Integer slot,
                   @Param("item") Item item,
                   @Param("ammo") Integer ammo);
}
