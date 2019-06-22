package toker.warbandscripts.panel.entity;

import javax.persistence.*;
import java.util.Collection;
import java.util.Objects;

@Entity
@Table(name = "chest", schema = "pax", catalog = "")
public class Chest {
    private Integer id;
    private String name;
    private Integer size;
    private Collection<ChestSlot> chestSlotsById;

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
    @Column(name = "size", nullable = false)
    public Integer getSize() {
        return size;
    }

    public void setSize(Integer size) {
        this.size = size;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Chest chest = (Chest) o;
        return Objects.equals(id, chest.id) &&
                Objects.equals(name, chest.name) &&
                Objects.equals(size, chest.size);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, size);
    }

    @OneToMany(mappedBy = "chestByChestId")
    public Collection<ChestSlot> getChestSlotsById() {
        return chestSlotsById;
    }

    public void setChestSlotsById(Collection<ChestSlot> chestSlotsById) {
        this.chestSlotsById = chestSlotsById;
    }
}
