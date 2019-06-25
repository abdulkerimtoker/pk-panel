package toker.warbandscripts.panel.entity;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;

@Entity
@Table(name = "inventory_slot", schema = "pax")
@IdClass(InventorySlotPK.class)
public class InventorySlot {
    private Integer slot;
    private Integer ammo;
    private Inventory inventoryByInventoryId;
    private Item itemByItemId;

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
        return Objects.equals(itemByItemId, that.itemByItemId) &&
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
    public Inventory getInventoryByInventoryId() {
        return inventoryByInventoryId;
    }

    public void setInventoryByInventoryId(Inventory inventoryByInventoryId) {
        this.inventoryByInventoryId = inventoryByInventoryId;
    }

    @ManyToOne
    @JoinColumn(name = "item_id", referencedColumnName = "id", nullable = false)
    public Item getItemByItemId() {
        return itemByItemId;
    }

    public void setItemByItemId(Item itemByItemId) {
        this.itemByItemId = itemByItemId;
    }
}

class InventorySlotPK implements Serializable {
    private Integer slot;
    private Inventory inventoryByInventoryId;

    public Integer getSlot() {
        return slot;
    }

    public Inventory getInventoryByInventoryId() {
        return inventoryByInventoryId;
    }

    public void setSlot(Integer slot) {
        this.slot = slot;
    }

    public void setInventoryByInventoryId(Inventory inventoryByInventoryId) {
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