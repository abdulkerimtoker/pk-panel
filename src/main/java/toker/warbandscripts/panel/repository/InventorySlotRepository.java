package toker.warbandscripts.panel.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import toker.warbandscripts.panel.entity.InventorySlot;

public interface InventorySlotRepository extends JpaRepository<InventorySlot, Integer>, JpaSpecificationExecutor<InventorySlot> {
    InventorySlot findByInventoryIdAndSlot(Integer inventoryId, Integer slot);
}
