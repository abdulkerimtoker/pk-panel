package toker.warbandscripts.panel.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import org.springframework.data.jpa.repository.EntityGraph;

import javax.persistence.*;
import java.util.Collection;
import java.util.Objects;

@Entity
@Table(name = "inventory")
public class Inventory {
    private Integer id;
    private Integer size;
    private Player player;
    private Collection<InventorySlot> slots;

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
        Inventory inventory = (Inventory) o;
        return Objects.equals(id, inventory.id) &&
                Objects.equals(size, inventory.size);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, size);
    }

    @ManyToOne
    @JoinColumn(name = "player_id", referencedColumnName = "id", nullable = false)
    @JsonIgnore
    public Player getPlayer() {
        return player;
    }

    public void setPlayer(Player playerByPlayerId) {
        this.player = playerByPlayerId;
    }

    @OneToMany(mappedBy = "inventory", cascade = {CascadeType.MERGE, CascadeType.PERSIST})
    public Collection<InventorySlot> getSlots() {
        return slots;
    }

    public void setSlots(Collection<InventorySlot> inventorySlotsById) {
        this.slots = inventorySlotsById;
    }
}
