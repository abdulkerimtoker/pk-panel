package toker.warbandscripts.panel.model;

import toker.warbandscripts.panel.entity.NoticeBoard;

import java.util.List;

public class BoardListModel {
    private List<NoticeBoard> boards;

    public List<NoticeBoard> getBoards() {
        return boards;
    }

    public void setBoards(List<NoticeBoard> boards) {
        this.boards = boards;
    }
}
