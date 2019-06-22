package toker.warbandscripts.panel.repository;

import org.springframework.stereotype.Repository;
import toker.warbandscripts.panel.entity.DoorKey;
import toker.warbandscripts.panel.entity.NoticeBoard;
import toker.warbandscripts.panel.entity.NoticeBoardAccess;
import toker.warbandscripts.panel.entity.Player;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import java.util.List;

@Repository
@Transactional
public class BoardRepository {

    @PersistenceContext
    private EntityManager em;

    public List<NoticeBoard> getBoards() {
        return em.createQuery("FROM NoticeBoard").getResultList();
    }

    public NoticeBoard getBoardById(int id) {
        return em.find(NoticeBoard.class, id);
    }

    public boolean updateBoard(NoticeBoard board) {
        NoticeBoard found = em.find(NoticeBoard.class, board.getId());
        if (found != null) {
            found.setName(board.getName());
            return true;
        }
        return false;
    }

    public boolean addBoardAccess(int playerId, int boardId, boolean isOwner) {
        Player player = em.find(Player.class, playerId);
        NoticeBoard board = em.find(NoticeBoard.class, boardId);

        if (player != null && board != null) {
            createBoardAccessEntity(board, player, isOwner);
            return true;
        }

        return false;
    }

    private void createBoardAccessEntity(NoticeBoard board, Player player, boolean isOwner) {
        NoticeBoardAccess boardAccess = new NoticeBoardAccess();
        boardAccess.setNoticeBoardByBoardId(board);
        boardAccess.setPlayerByUserId(player);
        boardAccess.setIsOwner(isOwner);
        em.merge(boardAccess);
    }

    public NoticeBoardAccess getBoardAccessById(int boardAccessId) {
        return em.find(NoticeBoardAccess.class, boardAccessId);
    }

    public void removeBoardAccess(int boardAccessId) {
        deleteBoardAccessEntity(getBoardAccessById(boardAccessId));
    }

    private void deleteBoardAccessEntity(NoticeBoardAccess doorKey) {
        em.remove(doorKey);
    }
}
