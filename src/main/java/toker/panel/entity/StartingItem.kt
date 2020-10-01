package toker.panel.entity

import toker.panel.entity.pk.StartingItemPK
import javax.persistence.*

@Entity
@Table(name = "starting_item")
@IdClass(StartingItemPK::class)
class StartingItem(
        @Id
        @ManyToOne
        @JoinColumn(name = "server_id", referencedColumnName = "id")
        var server: Server? = null,

        @Id
        @OneToOne
        @JoinColumn(name = "item_id", referencedColumnName = "id")
        var item: Item? = null
) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false
        other as StartingItem
        if (server != other.server) return false
        if (item != other.item) return false
        return true
    }

    override fun hashCode(): Int {
        var result = server?.hashCode() ?: 0
        result = 31 * result + (item?.hashCode() ?: 0)
        return result
    }
}