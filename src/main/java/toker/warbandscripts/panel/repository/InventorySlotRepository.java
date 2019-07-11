package toker.warbandscripts.panel.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;
import toker.warbandscripts.panel.entity.Inventory;
import toker.warbandscripts.panel.entity.InventorySlot;
import toker.warbandscripts.panel.entity.Item;

import java.util.Optional;

public interface InventorySlotRepository extends JpaRepository<InventorySlot, Integer>, JpaSpecificationExecutor<InventorySlot> {
    Optional<InventorySlot> findByInventoryIdAndSlot(Integer inventoryId, Integer slot);

    @Modifying
    @Transactional
    @Query("UPDATE InventorySlot SET item = ?3 WHERE inventory = ?1 AND slot = ?2")
    int updateSlot(Inventory inventory, Integer slot, Item item, Integer ammo);
}
