package toker.panel.entity

import javax.persistence.*

@Entity
@Table(name = "panel_user_authority_assignment")
class PanelUserAuthorityAssignment(
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        @Column(name = "id", nullable = false)
        var id: Int? = null,

        @ManyToOne
        @JoinColumn(name = "panel_user_id", referencedColumnName = "id", nullable = false)
        var panelUser: PanelUser? = null,

        @ManyToOne
        @JoinColumn(name = "server_id", referencedColumnName = "id", nullable = false)
        var server: Server? = null,

        @ManyToOne
        @JoinColumn(name = "authority_id", referencedColumnName = "id", nullable = false)
        var authority: PanelUserAuthority? = null
)