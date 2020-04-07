package toker.warbandscripts.panel.repository;

import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import toker.warbandscripts.panel.entity.Faction;
import toker.warbandscripts.panel.entity.Troop;

import java.util.List;
import java.util.Optional;

public interface TroopRepository extends JpaRepository<Troop, Integer>, JpaSpecificationExecutor<Troop> {
}
