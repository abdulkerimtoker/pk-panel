package toker.panel.repository

import toker.panel.entity.NoticeBoard
import toker.panel.entity.pk.NoticeBoardPK

interface NoticeBoardRepository : BaseRepository<NoticeBoard, NoticeBoardPK> {
    fun findAllByServerId(serverId: Int): List<NoticeBoard>
}