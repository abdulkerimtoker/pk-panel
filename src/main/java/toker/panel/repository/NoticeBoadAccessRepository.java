package toker.panel.repository;

import toker.panel.entity.NoticeBoardAccess;

import java.util.List;

public interface NoticeBoadAccessRepository extends BaseRepository<NoticeBoardAccess, Integer>{
    List<NoticeBoardAccess> findAllByPlayerId(Integer playerId);
}
