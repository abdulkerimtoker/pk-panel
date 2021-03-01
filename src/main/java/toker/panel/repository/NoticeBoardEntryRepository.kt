package toker.panel.repository

import org.springframework.data.jpa.repository.Modifying
import org.springframework.transaction.annotation.Transactional
import toker.panel.entity.NoticeBoard
import toker.panel.entity.NoticeBoardEntry

interface NoticeBoardEntryRepository : BaseRepository<NoticeBoardEntry, Int> {
    fun findAllByBoardAndEntryNo(board: NoticeBoard, entryNo: Int): List<NoticeBoardEntry>

    @Transactional
    @Modifying
    fun deleteByBoardAndEntryNo(board: NoticeBoard, entryNo: Int): Int
}