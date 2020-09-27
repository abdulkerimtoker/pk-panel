package toker.panel.repository

import toker.panel.entity.ServerConfiguration
import java.util.*

interface ServerConfigurationRepository : BaseRepository<ServerConfiguration, Int> {
    fun findByServerIdAndName(serverId: Int, name: String): Optional<ServerConfiguration>
    fun findAllByServerId(serverId: Int): Collection<ServerConfiguration>
}