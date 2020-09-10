package toker.warbandscripts.panel.entity;

import toker.warbandscripts.panel.entity.pk.ChestSlotPK;
import javax.persistence.*;

@Entity
@Table(name = "chest_slot")
@IdClass(ChestSlotPK.class)
public class ChestSlot {

    private Chest chest;
    private Integer slot;
    private Integer ammo;
    private Item item;

    @Id
    @ManyToOne
    @JoinColumns({
            @JoinColumn(name = "chest_index", referencedColumnName = "index"),
            @JoinColumn(name = "chest_server_id", referencedColumnName = "server_id")
    })
    public Chest getChest() {
        return chest;
    }

    public void setChest(Chest chest) {
        this.chest = chest;
    }

    @Id
    @Column(name = "slot")
    public Integer getSlot() {
        return slot;
    }

    public void setSlot(Integer slot) {
        this.slot = slot;
    }

    @ManyToOne
    @JoinColumn(name = "item_id", referencedColumnName = "id", nullable = false)
    public Item getItem() {
        return item;
    }

    public void setItem(Item item) {
        this.item = item;
    }

    @Column(name = "ammo", nullable = false)
    public Integer getAmmo() {
        return ammo;
    }

    public void setAmmo(Integer ammo) {
        this.ammo = ammo;
    }

}

