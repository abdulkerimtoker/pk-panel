package toker.warbandscripts.panel.entity;

import com.fasterxml.jackson.annotation.JsonView;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "notice_board_access")
public class NoticeBoardAccess {
    private Integer id;
    private Boolean isOwner = false;
    private NoticeBoard board;
    private Player player;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    @Basic
    @Column(name = "is_owner", nullable = false)
    public Boolean getIsOwner() {
        return isOwner;
    }

    public void setIsOwner(Boolean isOwner) {
        this.isOwner = isOwner;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        NoticeBoardAccess that = (NoticeBoardAccess) o;
        return Objects.equals(id, that.id) &&
                Objects.equals(isOwner, that.isOwner);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, isOwner);
    }

    @ManyToOne
    @JoinColumns({
            @JoinColumn(name = "board_index", referencedColumnName = "index", nullable = false),
            @JoinColumn(name = "board_server_id", referencedColumnName = "server_id", nullable = false)
    })
    @JsonView(View.Board.class)
    public NoticeBoard getBoard() {
        return board;
    }

    public void setBoard(NoticeBoard board) {
        this.board = board;
    }

    @ManyToOne
    @JoinColumn(name = "user_id", referencedColumnName = "id", nullable = false)
    @JsonView(View.Player.class)
    public Player getPlayer() {
        return player;
    }

    public void setPlayer(Player player) {
        this.player = player;
    }

    public static class View {
        public static class Board {}
        public static class Player {}
    }
}
