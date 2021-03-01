package toker.panel.repository

import toker.panel.entity.NoticeBoard
import toker.panel.entity.NoticeBoardAccess
import toker.panel.entity.pk.NoticeBoardPK

interface NoticeBoardAccessRepository : BaseRepository<NoticeBoardAccess, Int> {
    fun countByBoardAndPlayerIdAndIsOwner(boardId: NoticeBoard, playerId: Int, isOwner: Boolean): Int
    fun countByBoardAndPlayerId(boardId: NoticeBoard, playerId: Int): Int
}