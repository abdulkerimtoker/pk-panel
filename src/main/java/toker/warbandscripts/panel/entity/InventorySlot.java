package toker.warbandscripts.panel.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;

@Entity
@Table(name = "inventory_slot", schema = "pax")
@IdClass(InventorySlotPK.class)
public class InventorySlot {
    private Integer slot;
    private Integer ammo;
    private Inventory inventory;
    private Item item;

    @Id
    @Column(name = "slot", nullable = false)
    public Integer getSlot() {
        return slot;
    }

    public void setSlot(Integer slot) {
        this.slot = slot;
    }

    @Basic
    @Column(name = "ammo", nullable = false)
    public Integer getAmmo() {
        return ammo;
    }

    public void setAmmo(Integer ammo) {
        this.ammo = ammo;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        InventorySlot that = (InventorySlot) o;
        return Objects.equals(item, that.item) &&
                Objects.equals(slot, that.slot) &&
                Objects.equals(ammo, that.ammo);
    }

    @Override
    public int hashCode() {
        return Objects.hash(slot, ammo);
    }

    @Id
    @ManyToOne
    @JoinColumn(name = "inventory_id", referencedColumnName = "id", nullable = false)
    @JsonBackReference
    public Inventory getInventory() {
        return inventory;
    }

    public void setInventory(Inventory inventoryByInventoryId) {
        this.inventory = inventoryByInventoryId;
    }

    @ManyToOne
    @JoinColumn(name = "item_id", referencedColumnName = "id", nullable = false)
    public Item getItem() {
        return item;
    }

    public void setItem(Item itemByItemId) {
        this.item = itemByItemId;
    }
}

class InventorySlotPK implements Serializable {
    private Integer slot;
    private Inventory inventoryByInventoryId;

    public Integer getSlot() {
        return slot;
    }

    public Inventory getInventory() {
        return inventoryByInventoryId;
    }

    public void setSlot(Integer slot) {
        this.slot = slot;
    }

    public void setInventory(Inventory inventoryByInventoryId) {
        this.inventoryByInventoryId = inventoryByInventoryId;
    }

    @Override
    public int hashCode() {
        return Objects.hash(slot, inventoryByInventoryId);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;

        InventorySlotPK other = (InventorySlotPK)obj;
        return this.slot.equals(other.slot) &&
                this.inventoryByInventoryId.equals(other.inventoryByInventoryId);
    }
}