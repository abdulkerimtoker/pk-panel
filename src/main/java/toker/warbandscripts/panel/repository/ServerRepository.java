package toker.warbandscripts.panel.repository;

import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import toker.warbandscripts.panel.entity.Server;

public interface ServerRepository extends JpaRepository<Server, Integer>, JpaSpecificationExecutor<Server> {
    @Cacheable("server")
    Server findByKey(String key);
}
