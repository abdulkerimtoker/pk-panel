package toker.warbandscripts.panel.entity;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "chest_slot", schema = "pax", catalog = "")
public class ChestSlot {
    private Integer slot;
    private Integer ammo;
    private Chest chestByChestId;
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
        ChestSlot chestSlot = (ChestSlot) o;
        return Objects.equals(slot, chestSlot.slot) &&
                Objects.equals(ammo, chestSlot.ammo);
    }

    @Override
    public int hashCode() {
        return Objects.hash(slot, ammo);
    }

    @ManyToOne
    @JoinColumn(name = "chest_id", referencedColumnName = "id", nullable = false)
    public Chest getChestByChestId() {
        return chestByChestId;
    }

    public void setChestByChestId(Chest chestByChestId) {
        this.chestByChestId = chestByChestId;
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
