package toker.warbandscripts.panel.entity.pk;


import java.io.Serializable;
import java.util.Objects;

public class ChestSlotPK implements Serializable {

    private Integer slot;
    private ChestPK chest;

    public Integer getSlot() {
        return slot;
    }

    public void setSlot(Integer slot) {
        this.slot = slot;
    }

    public ChestPK getChest() {
        return chest;
    }

    public void setChest(ChestPK chest) {
        this.chest = chest;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ChestSlotPK that = (ChestSlotPK) o;
        return Objects.equals(slot, that.slot) &&
                Objects.equals(chest, that.chest);
    }

    @Override
    public int hashCode() {
        return Objects.hash(slot, chest);
    }
}
