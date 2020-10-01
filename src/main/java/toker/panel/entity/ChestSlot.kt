package toker.panel.entity

import toker.panel.entity.pk.ChestSlotPK
import javax.persistence.*

@Entity
@Table(name = "chest_slot")
@IdClass(ChestSlotPK::class)
class ChestSlot(
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
) {
        override fun equals(other: Any?): Boolean {
                if (this === other) return true
                if (javaClass != other?.javaClass) return false
                other as ChestSlot
                if (chest != other.chest) return false
                if (slot != other.slot) return false
                return true
        }

        override fun hashCode(): Int {
                var result = chest?.hashCode() ?: 0
                result = 31 * result + (slot ?: 0)
                return result
        }
}