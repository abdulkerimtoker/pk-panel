package toker.warbandscripts.panel.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import toker.warbandscripts.panel.entity.ServerConfiguration;

import java.util.Collection;
import java.util.Optional;

public interface ServerConfigurationRepository extends BaseRepository<ServerConfiguration, Integer> {

    Optional<ServerConfiguration> findByServerIdAndName(Integer serverId, String name);
    Collection<ServerConfiguration> findAllByServerId(Integer serverId);
}
