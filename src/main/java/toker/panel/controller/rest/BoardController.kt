package toker.panel.controller.rest

import com.fasterxml.jackson.annotation.JsonView
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController
import toker.panel.entity.NoticeBoard
import toker.panel.entity.NoticeBoardAccess
import toker.panel.entity.NoticeBoardAccess.View.Board
import toker.panel.service.BoardService

@RestController
class BoardController(private val boardService: BoardService) {
    @GetMapping("/api/board")
    @JsonView(NoticeBoard.View.None::class)
    fun boards(): List<NoticeBoard> {
        return boardService.allBoards
    }

    @PutMapping("/api/player/boardAccess")
    @JsonView(Board::class)
    fun saveBoardAccess(@RequestBody boardAccess: NoticeBoardAccess?): NoticeBoardAccess {
        return boardService.saveBoardAccess(boardAccess!!)
    }
}