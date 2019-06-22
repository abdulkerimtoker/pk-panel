package toker.warbandscripts.panel.entity;

import javax.persistence.*;
import java.util.Collection;
import java.util.Objects;

@Entity
@Table(name = "notice_board", schema = "pax", catalog = "")
public class NoticeBoard {
    private Integer id;
    private String name;
    private Collection<NoticeBoardAccess> noticeBoardAccessesById;
    private Collection<NoticeBoardEntry> noticeBoardEntriesById;

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
    @Column(name = "name", nullable = false, length = 64)
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        NoticeBoard that = (NoticeBoard) o;
        return Objects.equals(id, that.id) &&
                Objects.equals(name, that.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name);
    }

    @OneToMany(mappedBy = "noticeBoardByBoardId")
    public Collection<NoticeBoardAccess> getNoticeBoardAccessesById() {
        return noticeBoardAccessesById;
    }

    public void setNoticeBoardAccessesById(Collection<NoticeBoardAccess> noticeBoardAccessesById) {
        this.noticeBoardAccessesById = noticeBoardAccessesById;
    }

    @OneToMany(mappedBy = "noticeBoardByBoardId")
    public Collection<NoticeBoardEntry> getNoticeBoardEntriesById() {
        return noticeBoardEntriesById;
    }

    public void setNoticeBoardEntriesById(Collection<NoticeBoardEntry> noticeBoardEntriesById) {
        this.noticeBoardEntriesById = noticeBoardEntriesById;
    }
}
