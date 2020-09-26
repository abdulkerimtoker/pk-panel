package toker.panel.entity

import com.fasterxml.jackson.annotation.JsonView
import toker.panel.entity.pk.FactionPK
import javax.persistence.*

@Entity
@Table(name = "faction")
@IdClass(FactionPK::class)
data class Faction(
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
        var bannerId: Int? = null,

        @OneToMany(mappedBy = "faction")
        @JsonView(View.Players::class)
        var players: MutableSet<Player>? = null
) {
    interface View {
        interface None
        interface Players
    }
}