package toker.warbandscripts.panel.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import toker.warbandscripts.panel.entity.Chest;
import toker.warbandscripts.panel.entity.pk.ChestPK;

public interface ChestRepository extends BaseRepository<Chest, ChestPK> {
}
