package toker.panel.entity;

import com.fasterxml.jackson.annotation.JsonView;
import toker.panel.entity.pk.DoorPK;

import javax.persistence.*;
import java.util.Collection;
import java.util.Objects;

@Entity
@Table(name = "door")
@IdClass(DoorPK.class)
public class Door {

    private Integer index;
    private Server server;
    private String name;
    private Boolean locked;
    private Collection<DoorKey> doorKeys;

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

    @Column(name = "name", nullable = false, length = 32)
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Column(name = "locked", nullable = false)
    public Boolean getLocked() {
        return locked;
    }

    public void setLocked(Boolean locked) {
        this.locked = locked;
    }

    @OneToMany(mappedBy = "door")
    @JsonView(View.DoorKeys.class)
    public Collection<DoorKey> getDoorKeys() {
        return doorKeys;
    }

    public void setDoorKeys(Collection<DoorKey> doorKeys) {
        this.doorKeys = doorKeys;
    }

    public static class View {
        public static class DoorKeys {}
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Door door = (Door) o;
        return Objects.equals(index, door.index) &&
                Objects.equals(server, door.server);
    }

    @Override
    public int hashCode() {
        return Objects.hash(index, name, locked);
    }
}

