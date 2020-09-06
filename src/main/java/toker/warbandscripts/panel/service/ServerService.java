package toker.warbandscripts.panel.service;

import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.stereotype.Service;
import toker.warbandscripts.panel.entity.Server;
import toker.warbandscripts.panel.entity.ServerConfiguration;
import toker.warbandscripts.panel.repository.ServerConfigurationRepository;
import toker.warbandscripts.panel.repository.ServerRepository;

import javax.persistence.criteria.JoinType;
import java.io.File;
import java.nio.file.Files;
import java.util.Collection;
import java.util.Optional;

@Service
public class ServerService {

    private ServerRepository serverRepository;
    private ServerConfigurationRepository serverConfigurationRepository;

    public ServerService(ServerRepository serverRepository,
                         ServerConfigurationRepository serverConfigurationRepository) {
        this.serverRepository = serverRepository;
        this.serverConfigurationRepository = serverConfigurationRepository;
    }

    public Server getServer(Integer serverId, String... with) throws ChangeSetPersister.NotFoundException {
        return serverRepository.findOne((root, query, builder) -> {
            for (String attr : with) {
                root.fetch(attr, JoinType.LEFT);
            }
            return builder.equal(root.get("id"), serverId);
        }).orElseThrow(ChangeSetPersister.NotFoundException::new);
    }

    public Server getServerByKey(String key) {
        return serverRepository.findByKey(key).orElse(null);
    }

    public Optional<ServerConfiguration> getServerConfiguration(int serverId, String name) {
        return serverConfigurationRepository.findByServerIdAndName(serverId, name);
    }

    public Collection<ServerConfiguration> getServerConfigurations(int serverId) {
        return serverConfigurationRepository.findAllByServerId(serverId);
    }

    public File getMapDir(Server server) {
        File exe = new File(server.getExePath());
        File serverDir = new File(exe.getParent());
        File modulesDir = new File(serverDir, "Modules");
        File moduleDir = new File(modulesDir, server.getModuleName());
        return new File(moduleDir, "SceneObj");
    }
}
