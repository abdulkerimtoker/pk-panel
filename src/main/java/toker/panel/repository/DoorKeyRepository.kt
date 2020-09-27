package toker.panel.repository

import toker.panel.entity.DoorKey

interface DoorKeyRepository : BaseRepository<DoorKey, Int> {
    fun findAllByPlayerId(playerId: Int): List<DoorKey>
}