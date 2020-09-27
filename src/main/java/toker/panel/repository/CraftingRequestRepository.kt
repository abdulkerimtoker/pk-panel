package toker.panel.repository

import toker.panel.entity.CraftingRequest

interface CraftingRequestRepository : BaseRepository<CraftingRequest, Int> {
    fun findAllByPlayerId(playerId: Int): List<CraftingRequest>
}