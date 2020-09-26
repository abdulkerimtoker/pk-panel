package toker.panel.entity

import com.fasterxml.jackson.annotation.JsonBackReference
import com.fasterxml.jackson.annotation.JsonIgnore
import toker.panel.entity.pk.InventorySlotPK
import java.util.*
import javax.persistence.*

@Entity
@Table(name = "inventory_slot")
@IdClass(InventorySlotPK::class)
data class InventorySlot(
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
)