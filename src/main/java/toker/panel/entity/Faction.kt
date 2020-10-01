package toker.panel.entity

import com.fasterxml.jackson.annotation.JsonView
import toker.panel.entity.pk.FactionPK
import javax.persistence.*

@Entity
@Table(name = "faction")
@IdClass(FactionPK::class)
class Faction(
        @Id
        @Column(name = "index", nullable = false)
        var index: Int? = null,

        @Id
        @ManyToOne
        @JoinColumn(name = "server_id", referencedColumnName = "id", nullable = false)
        var server: Server? = null,

        @Column(name = "name", nullable = false, length = 64)
        var name: String? = null,

        @Column(name = "banner_id", nullable = false)
        var bannerId: Int? = null
) {
    @OneToMany(mappedBy = "faction")
    @JsonView(View.Players::class)
    var players: MutableSet<Player>? = null

    interface View {
        interface None
        interface Players
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false
        other as Faction
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