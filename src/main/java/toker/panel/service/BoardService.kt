package toker.panel.service

import org.springframework.stereotype.Service
import toker.panel.entity.NoticeBoard
import toker.panel.entity.NoticeBoardAccess
import toker.panel.repository.NoticeBoadAccessRepository
import toker.panel.repository.NoticeBoardRepository

@Service
class BoardService(private val noticeBoardRepository: NoticeBoardRepository,
                   private val noticeBoadAccessRepository: NoticeBoadAccessRepository) {
    val allBoards: List<NoticeBoard>
        get() = noticeBoardRepository.findAll()

    fun saveBoardAccess(boardAccess: NoticeBoardAccess): NoticeBoardAccess {
        return noticeBoadAccessRepository.saveAndFlush(boardAccess)
    }
}