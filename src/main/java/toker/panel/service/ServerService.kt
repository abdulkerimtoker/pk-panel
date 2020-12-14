package toker.panel.service

import org.springframework.data.crossstore.ChangeSetPersister
import org.springframework.stereotype.Service
import toker.panel.entity.*
import toker.panel.repository.FactionRepository
import toker.panel.repository.ServerConfigurationRepository
import toker.panel.repository.ServerRepository
import toker.panel.repository.ServerStartupCommandRepository
import java.io.File
import java.util.*
import javax.persistence.criteria.CriteriaBuilder
import javax.persistence.criteria.JoinType
import javax.persistence.criteria.Root

@Service
class ServerService(private val serverRepository: ServerRepository,
                    private val serverConfigurationRepository: ServerConfigurationRepository,
                    private val serverStartupCommandRepository: ServerStartupCommandRepository,
                    private val factionRepository: FactionRepository) {

    @Throws(ChangeSetPersister.NotFoundException::class)
    fun getServer(serverId: Int?, vararg with: String?): Server {
        return serverRepository.findOne { root: Root<Server?>, _, builder: CriteriaBuilder ->
            for (attr in with) {
                root.fetch<Any, Any>(attr, JoinType.LEFT)
            }
            builder.equal(root.get<Any>("id"), serverId)
        }.orElseThrow { ChangeSetPersister.NotFoundException() }
    }

    fun getServerByKey(key: String): Server {
        return serverRepository.findByKey(key).orElse(null)
    }

    fun getServerConfiguration(serverId: Int, name: String): Optional<ServerConfiguration> {
        return serverConfigurationRepository.findByServerIdAndName(serverId, name)
    }

    fun getServerConfigurations(serverId: Int): Collection<ServerConfiguration> {
        return serverConfigurationRepository.findAllByServerId(serverId)
    }

    fun getModuleDir(server: Server): File {
        val exe = File(server.exePath!!)
        val serverDir = File(exe.parent)
        val modulesDir = File(serverDir, "Modules")
        return File(modulesDir, server.moduleName!!)
    }

    fun getMapDir(server: Server): File {
        return File(getModuleDir(server), "SceneObj")
    }

    fun getStartupCommands(serverId: Int): List<ServerStartupCommand> {
        return serverStartupCommandRepository.findAll { root, _, builder ->
            builder.equal(root.get(ServerStartupCommand_.server).get(Server_.id), serverId)
        }
    }

    fun getFactions(serverId: Int): List<Faction> = factionRepository.findAllByServerId(serverId)
}