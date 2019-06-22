package toker.warbandscripts.panel.entity;

import javax.persistence.*;
import java.util.Collection;
import java.util.Objects;

@Entity
@Table(name = "door", schema = "pax", catalog = "")
public class Door {
    private Integer id;
    private String name;
    private Boolean locked;
    private Collection<DoorKey> doorKeysById;

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
    @Column(name = "name", nullable = false, length = 32)
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Basic
    @Column(name = "locked", nullable = false)
    public Boolean getLocked() {
        return locked;
    }

    public void setLocked(Boolean locked) {
        this.locked = locked;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Door door = (Door) o;
        return Objects.equals(id, door.id) &&
                Objects.equals(name, door.name) &&
                Objects.equals(locked, door.locked);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, locked);
    }

    @OneToMany(mappedBy = "doorByDoorId")
    public Collection<DoorKey> getDoorKeysById() {
        return doorKeysById;
    }

    public void setDoorKeysById(Collection<DoorKey> doorKeysById) {
        this.doorKeysById = doorKeysById;
    }
}
