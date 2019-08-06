package toker.warbandscripts.panel.controller.rest;

import com.fasterxml.jackson.annotation.JsonView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import toker.warbandscripts.panel.entity.NoticeBoard;
import toker.warbandscripts.panel.entity.NoticeBoardAccess;
import toker.warbandscripts.panel.service.BoardService;

import java.util.List;

@RestController
public class BoardController {

    @Autowired
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
    @JsonView(NoticeBoardAccess.View.Board.class)
    public NoticeBoardAccess saveBoardAccess(@RequestBody NoticeBoardAccess boardAccess) {
        return boardService.saveBoardAccess(boardAccess);
    }
}
