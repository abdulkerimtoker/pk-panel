package toker.panel.entity

import toker.panel.entity.pk.ChestPK
import java.util.*
import javax.persistence.*

@Entity
@Table(name = "chest")
@IdClass(ChestPK::class)
data class Chest(
        @Id
        @Column(name = "index", nullable = false)
        var index: Int? = null,

        @Id
        @ManyToOne
        @JoinColumn(name = "server_id", referencedColumnName = "id", nullable = false)
        var server: Server? = null,

        @Column(name = "name", nullable = false, length = 64)
        var name: String? = null,

        @Column(name = "size", nullable = false)
        var size: Int? = null,

        @OneToMany(mappedBy = "chest")
        var slots: MutableSet<ChestSlot>? = null
)