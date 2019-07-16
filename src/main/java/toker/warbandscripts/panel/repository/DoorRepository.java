package toker.warbandscripts.panel.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import toker.warbandscripts.panel.entity.Door;

public interface DoorRepository extends JpaRepository<Door, Integer>, JpaSpecificationExecutor<Door> {
}
