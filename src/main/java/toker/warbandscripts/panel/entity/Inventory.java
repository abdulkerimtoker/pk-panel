package toker.warbandscripts.panel.entity;

import javax.persistence.*;
import java.util.Collection;
import java.util.Objects;

@Entity
@Table(name = "inventory", schema = "pax", catalog = "")
public class Inventory {
    private Integer id;
    private Integer size;
    private Player playerByPlayerId;
    private Collection<InventorySlot> inventorySlotsById;

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
    public Player getPlayerByPlayerId() {
        return playerByPlayerId;
    }

    public void setPlayerByPlayerId(Player playerByPlayerId) {
        this.playerByPlayerId = playerByPlayerId;
    }

    @OneToMany(mappedBy = "inventoryByInventoryId", fetch = FetchType.EAGER)
    public Collection<InventorySlot> getInventorySlotsById() {
        return inventorySlotsById;
    }

    public void setInventorySlotsById(Collection<InventorySlot> inventorySlotsById) {
        this.inventorySlotsById = inventorySlotsById;
    }
}
