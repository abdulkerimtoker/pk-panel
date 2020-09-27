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
)