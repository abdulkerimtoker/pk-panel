package toker.warbandscripts.panel.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import toker.warbandscripts.panel.entity.PanelUserRank;

public interface PanelUserRankRepository extends BaseRepository<PanelUserRank, Integer> {
}
