package toker.panel.repository

import toker.panel.entity.Faction
import toker.panel.entity.pk.FactionPK

interface FactionRepository : BaseRepository<Faction, FactionPK> {
    fun findAllByServerId(serverId: Int): List<Faction>
}