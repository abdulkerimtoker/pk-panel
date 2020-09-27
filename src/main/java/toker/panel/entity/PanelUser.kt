package toker.panel.entity

import com.fasterxml.jackson.annotation.JsonView
import toker.panel.entity.PanelUser.View.AuthorityAssignments
import toker.panel.entity.PanelUser.View.Bans
import java.sql.Timestamp
import javax.persistence.*

@Entity
@Table(name = "panel_user")
class PanelUser(
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        @Column(name = "id", nullable = false)
        var id: Int? = null,

        @Column(name = "username", nullable = false, unique = true, length = 64)
        var username: String? = null,

        @Column(name = "claimed_identity", unique = true, length = 128)
        var claimedIdentity: String? = null,

        @Column(name = "creation_time", nullable = false)
        var creationTime: Timestamp? = null,

        @Column(name = "is_locked", nullable = false)
        var isLocked: Boolean? = null,

        @ManyToOne
        @JoinColumn(name = "rank_id", referencedColumnName = "id", nullable = false)
        var rank: PanelUserRank? = null
) {
    @OneToMany(mappedBy = "panelUser")
    @JsonView(Bans::class)
    var bans: Collection<Ban>? = null

    @OneToMany(mappedBy = "panelUser")
    @JsonView(AuthorityAssignments::class)
    var authorityAssignments: MutableSet<PanelUserAuthorityAssignment>? = null

    interface View {
        interface None
        interface Bans
        interface AuthorityAssignments
    }
}