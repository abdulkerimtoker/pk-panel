package toker.warbandscripts.panel.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import toker.warbandscripts.panel.entity.Faction;

public interface FactionRepository extends JpaRepository<Faction, Integer>, JpaSpecificationExecutor<Faction> {
}