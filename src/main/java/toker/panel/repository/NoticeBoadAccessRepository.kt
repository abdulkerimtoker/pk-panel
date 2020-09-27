package toker.panel.repository

import toker.panel.entity.NoticeBoardAccess

interface NoticeBoadAccessRepository : BaseRepository<NoticeBoardAccess, Int> {
    fun findAllByPlayerId(playerId: Int): List<NoticeBoardAccess>
}