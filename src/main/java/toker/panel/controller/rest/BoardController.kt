package toker.panel.controller.rest;

import com.fasterxml.jackson.annotation.JsonView;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import toker.panel.entity.NoticeBoard;
import toker.panel.entity.NoticeBoardAccess;
import toker.panel.service.BoardService;

import java.util.List;

@RestController
public class BoardController {

    private BoardService boardService;

    public BoardController(BoardService boardService) {
        this.boardService = boardService;
    }

    @GetMapping("/api/board")
    @JsonView(NoticeBoard.View.None.class)
    public List<NoticeBoard> boards() {
        return boardService.getAllBoards();
    }

    @PutMapping("/api/player/boardAccess")
    @PreAuthorize("hasRole(@serverService.getServerRoleName('BOARD_MANAGER', #boardAccess.player))")
    @JsonView(NoticeBoardAccess.View.Board.class)
    public NoticeBoardAccess saveBoardAccess(@RequestBody NoticeBoardAccess boardAccess) {
        return boardService.saveBoardAccess(boardAccess);
    }
}
