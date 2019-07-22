package toker.warbandscripts.panel.entity;

import com.fasterxml.jackson.annotation.JsonView;

import javax.persistence.*;
import java.util.Collection;
import java.util.Objects;

@Entity
@Table(name = "notice_board")
public class NoticeBoard {
    private Integer id;
    private String name;
    private Collection<NoticeBoardAccess> accesses;
    private Collection<NoticeBoardEntry> entries;

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

    @OneToMany(mappedBy = "board")
    @JsonView(View.Accesses.class)
    public Collection<NoticeBoardAccess> getAccesses() {
        return accesses;
    }

    public void setAccesses(Collection<NoticeBoardAccess> accesses) {
        this.accesses = accesses;
    }

    @OneToMany(mappedBy = "board")
    @JsonView(View.Entries.class)
    public Collection<NoticeBoardEntry> getEntries() {
        return entries;
    }

    public void setEntries(Collection<NoticeBoardEntry> entries) {
        this.entries = entries;
    }

    public static class View {
        public static class Accesses {}
        public static class Entries {}
    }
}
