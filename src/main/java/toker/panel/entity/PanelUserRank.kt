package toker.panel.entity

import com.fasterxml.jackson.annotation.JsonView
import toker.panel.entity.PanelUserRank.View.PanelUsers
import javax.persistence.*

@Entity
@Table(name = "panel_user_rank")
class PanelUserRank(
        @Id
        @Column(name = "id", nullable = false)
        var id: Int? = null,

        @Column(name = "rank_name", nullable = false, length = 32)
        var rankName: String? = null,

        @Column(name = "description", nullable = false, length = 64)
        var description: String? = null
) {
    @OneToMany(mappedBy = "rank")
    @JsonView(PanelUsers::class)
    var panelUsers: Collection<PanelUser>? = null

    interface View {
        interface PanelUsers
    }
}