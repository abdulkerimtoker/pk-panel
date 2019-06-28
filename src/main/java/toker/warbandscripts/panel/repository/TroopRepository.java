package toker.warbandscripts.panel.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import toker.warbandscripts.panel.entity.Troop;

public interface TroopRepository extends JpaRepository<Troop, Integer>, JpaSpecificationExecutor<Troop> {
}
