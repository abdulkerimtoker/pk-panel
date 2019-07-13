package toker.warbandscripts.panel.entity;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "notice_board_access")
public class NoticeBoardAccess {
    private Integer id;
    private Boolean isOwner;
    private NoticeBoard noticeBoardByBoardId;
    private Player playerByUserId;

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
    @JoinColumn(name = "board_id", referencedColumnName = "id", nullable = false)
    public NoticeBoard getNoticeBoardByBoardId() {
        return noticeBoardByBoardId;
    }

    public void setNoticeBoardByBoardId(NoticeBoard noticeBoardByBoardId) {
        this.noticeBoardByBoardId = noticeBoardByBoardId;
    }

    @ManyToOne
    @JoinColumn(name = "user_id", referencedColumnName = "id", nullable = false)
    public Player getPlayerByUserId() {
        return playerByUserId;
    }

    public void setPlayerByUserId(Player playerByUserId) {
        this.playerByUserId = playerByUserId;
    }
}
