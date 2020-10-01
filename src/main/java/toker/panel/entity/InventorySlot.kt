package toker.panel.entity

import com.fasterxml.jackson.annotation.JsonIgnore
import toker.panel.entity.pk.InventorySlotPK
import javax.persistence.*

@Entity
@Table(name = "inventory_slot")
@IdClass(InventorySlotPK::class)
class InventorySlot(
        @Id
        @Column(name = "slot", nullable = false)
        var slot: Int? = null,

        @Column(name = "ammo", nullable = false)
        var ammo: Int? = null,

        @Id
        @ManyToOne
        @JoinColumn(name = "inventory_id", referencedColumnName = "id", nullable = false)
        @JsonIgnore
        var inventory: Inventory? = null,

        @ManyToOne
        @JoinColumn(name = "item_id", referencedColumnName = "id", nullable = false)
        var item: Item? = null
) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false
        other as InventorySlot
        if (slot != other.slot) return false
        if (inventory != other.inventory) return false
        return true
    }

    override fun hashCode(): Int {
        var result = slot ?: 0
        result = 31 * result + (inventory?.hashCode() ?: 0)
        return result
    }
}