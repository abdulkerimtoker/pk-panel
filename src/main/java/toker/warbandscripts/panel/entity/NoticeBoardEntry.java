package toker.warbandscripts.panel.entity;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "notice_board_entry")
public class NoticeBoardEntry {
    private Integer id;
    private String entryText;
    private Integer entryNo;
    private NoticeBoard noticeBoardByBoardId;

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
    @Column(name = "entry_text", nullable = false, length = 256)
    public String getEntryText() {
        return entryText;
    }

    public void setEntryText(String entryText) {
        this.entryText = entryText;
    }

    @Basic
    @Column(name = "entry_no", nullable = false)
    public Integer getEntryNo() {
        return entryNo;
    }

    public void setEntryNo(Integer entryNo) {
        this.entryNo = entryNo;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        NoticeBoardEntry that = (NoticeBoardEntry) o;
        return Objects.equals(id, that.id) &&
                Objects.equals(entryText, that.entryText) &&
                Objects.equals(entryNo, that.entryNo);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, entryText, entryNo);
    }

    @ManyToOne
    @JoinColumn(name = "board_id", referencedColumnName = "id", nullable = false)
    public NoticeBoard getNoticeBoardByBoardId() {
        return noticeBoardByBoardId;
    }

    public void setNoticeBoardByBoardId(NoticeBoard noticeBoardByBoardId) {
        this.noticeBoardByBoardId = noticeBoardByBoardId;
    }
}
