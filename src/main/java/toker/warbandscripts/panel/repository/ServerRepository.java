package toker.warbandscripts.panel.repository;

import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import toker.warbandscripts.panel.entity.Server;

import java.util.Optional;

public interface ServerRepository extends BaseRepository<Server, Integer> {
    Optional<Server> findByKey(String key);
}
