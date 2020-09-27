package toker.panel.repository;

import toker.panel.entity.Server;

import java.util.Optional;

public interface ServerRepository extends BaseRepository<Server, Integer> {
    Optional<Server> findByKey(String key);
}
