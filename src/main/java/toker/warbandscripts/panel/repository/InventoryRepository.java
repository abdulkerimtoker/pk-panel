package toker.warbandscripts.panel.repository;

import org.springframework.data.jpa.repository.*;
import toker.warbandscripts.panel.entity.Inventory;

import java.util.Optional;

public interface InventoryRepository extends JpaRepository<Inventory, Integer>, JpaSpecificationExecutor<Inventory> {
    Inventory findFirstByPlayerId(Integer playerId);
}
