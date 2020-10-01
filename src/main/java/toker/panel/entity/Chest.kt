package toker.panel.entity

import toker.panel.entity.pk.ChestPK
import java.util.*
import javax.persistence.*

@Entity
@Table(name = "chest")
@IdClass(ChestPK::class)
class Chest(
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
) {
    @OneToMany(mappedBy = "chest")
    var slots: MutableSet<ChestSlot>? = null

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false
        other as Chest
        if (index != other.index) return false
        if (server != other.server) return false
        return true
    }

    override fun hashCode(): Int {
        var result = index ?: 0
        result = 31 * result + (server?.hashCode() ?: 0)
        return result
    }
}