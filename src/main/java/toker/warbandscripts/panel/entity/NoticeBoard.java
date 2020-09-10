package toker.warbandscripts.panel.entity;

import com.fasterxml.jackson.annotation.JsonView;
import toker.warbandscripts.panel.entity.pk.NoticeBoardPK;

import javax.persistence.*;
import java.util.Collection;
import java.util.Objects;

@Entity
@Table(name = "notice_board")
@IdClass(NoticeBoardPK.class)
public class NoticeBoard {

    private Integer index;
    private Server server;
    private String name;
    private Collection<NoticeBoardAccess> accesses;
    private Collection<NoticeBoardEntry> entries;

    @Id
    @Column(name = "index", nullable = false)
    public Integer getIndex() {
        return index;
    }

    public void setIndex(Integer id) {
        this.index = id;
    }

    @Id
    @ManyToOne
    @JoinColumn(name = "server_id", referencedColumnName = "id", nullable = false)
    public Server getServer() {
        return server;
    }

    public void setServer(Server server) {
        this.server = server;
    }

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
        return Objects.equals(index, that.index) &&
                Objects.equals(name, that.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(index, name);
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
        public static class None {}
    }
}

