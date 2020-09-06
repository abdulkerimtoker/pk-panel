package toker.warbandscripts.panel.entity.pk;

import toker.warbandscripts.panel.entity.Inventory;

import java.io.Serializable;
import java.util.Objects;

public class InventorySlotPK implements Serializable {
    private Integer slot;
    private Integer inventory;

    public Integer getSlot() {
        return slot;
    }

    public Integer getInventory() {
        return inventory;
    }

    public void setSlot(Integer slot) {
        this.slot = slot;
    }

    public void setInventory(Integer inventory) {
        this.inventory = inventory;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        InventorySlotPK that = (InventorySlotPK)obj;
        return Objects.equals(this.slot, that.slot) && Objects.equals(this.inventory, that.inventory);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.slot, this.inventory);
    }
}
