package toker.warbandscripts.panel.entity;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;

@Entity
@Table(name = "chest_slot")
@IdClass(ChestSlotPK.class)
public class ChestSlot {
    private Integer slot;
    private Integer ammo;
    private Chest chest;
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
        ChestSlot chestSlot = (ChestSlot) o;
        return Objects.equals(slot, chestSlot.slot) &&
                Objects.equals(ammo, chestSlot.ammo);
    }

    @Override
    public int hashCode() {
        return Objects.hash(slot, ammo);
    }

    @Id
    @ManyToOne
    @JoinColumn(name = "chest_id", referencedColumnName = "id", nullable = false)
    public Chest getChest() {
        return chest;
    }

    public void setChest(Chest chestByChestId) {
        this.chest = chestByChestId;
    }

    @ManyToOne
    @JoinColumn(name = "item_id", referencedColumnName = "id", nullable = false)
    public Item getItem() {
        return item;
    }

    public void setItem(Item item) {
        this.item = item;
    }
}

class ChestSlotPK implements Serializable {
    private Integer chest;
    private Integer slot;

    public Integer getChest() {
        return chest;
    }

    public void setChest(Integer chest) {
        this.chest = chest;
    }

    public Integer getSlot() {
        return slot;
    }

    public void setSlot(Integer slot) {
        this.slot = slot;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        ChestSlotPK that = (ChestSlotPK)obj;
        return Objects.equals(this.slot, that.slot) && Objects.equals(this.chest, that.chest);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.slot, this.chest);
    }
}