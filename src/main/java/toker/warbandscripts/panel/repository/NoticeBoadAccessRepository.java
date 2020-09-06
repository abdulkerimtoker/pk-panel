package toker.warbandscripts.panel.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import toker.warbandscripts.panel.entity.NoticeBoardAccess;

import java.util.List;

public interface NoticeBoadAccessRepository extends BaseRepository<NoticeBoardAccess, Integer>{
    List<NoticeBoardAccess> findAllByPlayerId(Integer playerId);
}
