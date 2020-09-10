package toker.warbandscripts.panel.repository;

import toker.warbandscripts.panel.entity.Server;

import java.util.Optional;

public interface ServerRepository extends BaseRepository<Server, Integer> {
    Optional<Server> findByKey(String key);
}
