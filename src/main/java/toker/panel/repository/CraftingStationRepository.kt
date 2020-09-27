package toker.panel.repository

import toker.panel.entity.CraftingStation
import toker.panel.entity.pk.CraftingStationPK

interface CraftingStationRepository : BaseRepository<CraftingStation, CraftingStationPK> {
    fun findAllByServerId(serverId: Int): List<CraftingStation>
}