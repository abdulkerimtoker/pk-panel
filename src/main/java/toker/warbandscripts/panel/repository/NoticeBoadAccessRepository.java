package toker.warbandscripts.panel.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import toker.warbandscripts.panel.entity.NoticeBoardAccess;

import java.util.List;

public interface NoticeBoadAccessRepository extends JpaRepository<NoticeBoardAccess, Integer>, JpaSpecificationExecutor<NoticeBoardAccess> {
    List<NoticeBoardAccess> findAllByPlayerId(Integer playerId);
}
