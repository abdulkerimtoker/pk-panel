package toker.panel.repository

import toker.panel.entity.Server
import java.util.*

interface ServerRepository : BaseRepository<Server, Int> {
    fun findByKey(key: String): Optional<Server>
}