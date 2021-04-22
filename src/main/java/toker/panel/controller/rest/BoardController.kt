package toker.panel.controller.rest

import com.fasterxml.jackson.annotation.JsonView
import org.springframework.data.crossstore.ChangeSetPersister
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.web.bind.annotation.*
import toker.panel.bean.SelectedServerId
import toker.panel.entity.*
import toker.panel.entity.NoticeBoardAccess.View.Board
import toker.panel.entity.pk.DoorPK
import toker.panel.entity.pk.NoticeBoardPK
import toker.panel.repository.NoticeBoardRepository
import toker.panel.service.BoardService

@RestController
class BoardController(
    private val boardService: BoardService,
    private val boardRepository: NoticeBoardRepository
) {

    @GetMapping("/api/board")
    @JsonView(NoticeBoard.View.None::class)
    fun boards(): List<NoticeBoard> {
        return boardService.allBoards
    }

    @GetMapping("/api/board/{index}")
    @JsonView(NoticeBoard.View.Accesses::class)
    fun board(@PathVariable index: Int): NoticeBoard {
        return boardRepository.findOne { root, query, builder ->
            root.fetch(NoticeBoard_.accesses)
            query.distinct(true)
            builder.and(
                builder.equal(root.get(NoticeBoard_.index), index),
                builder.equal(root.get(NoticeBoard_.server).get(Server_.id), SelectedServerId),
            )
        }.orElseThrow { ChangeSetPersister.NotFoundException() }
    }

    @PutMapping("/api/board/{index}")
    @JsonView(NoticeBoard.View.None::class)
    fun board(@RequestBody door: NoticeBoard, @PathVariable index: Int): NoticeBoard {
        val newState = boardRepository.findById(NoticeBoardPK(index = index, server = SelectedServerId))
            .orElseThrow { ChangeSetPersister.NotFoundException() }
        newState.name = door.name
        return boardRepository.saveAndFlush(newState)
    }
}