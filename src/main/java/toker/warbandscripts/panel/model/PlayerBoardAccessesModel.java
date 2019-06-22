package toker.warbandscripts.panel.model;

import toker.warbandscripts.panel.entity.NoticeBoard;
import toker.warbandscripts.panel.entity.NoticeBoardAccess;
import toker.warbandscripts.panel.entity.Player;

import java.util.List;

public class PlayerBoardAccessesModel {
    private List<NoticeBoardAccess> boardAccesses;
    private Player player;

    public List<NoticeBoardAccess> getBoardAccesses() {
        return boardAccesses;
    }

    public void setBoardAccesses(List<NoticeBoardAccess> boardAccesses) {
        this.boardAccesses = boardAccesses;
    }

    public Player getPlayer() {
        return player;
    }

    public void setPlayer(Player player) {
        this.player = player;
    }
}
