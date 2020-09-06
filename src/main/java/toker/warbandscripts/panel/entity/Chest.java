package toker.warbandscripts.panel.entity;

import toker.warbandscripts.panel.entity.pk.ChestPK;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Collection;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "chest")
@IdClass(ChestPK.class)
public class Chest {

    private Integer index;
    private Server server;
    private String name;
    private Integer size;

    private Collection<ChestSlot> slots;

    @Id
    @Column(name = "index", nullable = false)
    public Integer getIndex() {
        return index;
    }

    public void setIndex(Integer index) {
        this.index = index;
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

    @Column(name = "size", nullable = false)
    public Integer getSize() {
        return size;
    }

    public void setSize(Integer size) {
        this.size = size;
    }

    @OneToMany(mappedBy = "chest")
    public Collection<ChestSlot> getSlots() {
        return slots;
    }

    public void setSlots(Collection<ChestSlot> slots) {
        this.slots = slots;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Chest chest = (Chest) o;
        return Objects.equals(index, chest.index) &&
                Objects.equals(server.getId(), chest.server.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(index, name, size);
    }
}
