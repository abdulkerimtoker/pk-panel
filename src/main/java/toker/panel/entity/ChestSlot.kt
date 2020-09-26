package toker.panel.entity

import toker.panel.entity.pk.ChestSlotPK
import javax.persistence.*

@Entity
@Table(name = "chest_slot")
@IdClass(ChestSlotPK::class)
data class ChestSlot(
        @Id
        @ManyToOne
        @JoinColumns(JoinColumn(name = "chest_index", referencedColumnName = "index"), JoinColumn(name = "chest_server_id", referencedColumnName = "server_id"))
        var chest: Chest? = null,

        @Id
        @Column(name = "slot")
        var slot: Int? = null,

        @Column(name = "ammo", nullable = false)
        var ammo: Int? = null,

        @ManyToOne
        @JoinColumn(name = "item_id", referencedColumnName = "id", nullable = false)
        var item: Item? = null
)