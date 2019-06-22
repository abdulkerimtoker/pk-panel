package toker.warbandscripts.panel.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import toker.warbandscripts.panel.model.BoardListModel;
import toker.warbandscripts.panel.model.BoardManageModel;
import toker.warbandscripts.panel.repository.BoardRepository;

@Controller
@RequestMapping("/board")
public class BoardController {

    private BoardRepository boardRepository;

    @Autowired
    public BoardController(BoardRepository boardRepository) {
        this.boardRepository = boardRepository;
    }

    @RequestMapping("list")
    public String list(Model model) {
        BoardListModel listModel = new BoardListModel();
        listModel.setBoards(boardRepository.getBoards());
        model.addAttribute("model", listModel);
        return "board/list";
    }

    @GetMapping("manage/{boardId}")
    public String manage(@ModelAttribute("model") BoardManageModel manageModel, @PathVariable int boardId) {
        manageModel.setBoard(boardRepository.getBoardById(boardId));
        return "board/manage";
    }

    @Secured("ROLE_BOARD_MANAGER")
    @PostMapping("manage")
    public String processManage(@ModelAttribute("model") BoardManageModel form, BindingResult bindingResult) {
        if (!bindingResult.hasErrors()) {
            boardRepository.updateBoard(form.getBoard());
        }
        return "redirect:manage/" + form.getBoard().getId();
    }

    @Secured("ROLE_BOARD_MANAGER")
    @PostMapping("removeboardaccess")
    public String removeBoardAccess(int boardAccessId, int boardId) {
        boardRepository.removeBoardAccess(boardAccessId);
        return "redirect:manage/" + boardId;
    }
}
