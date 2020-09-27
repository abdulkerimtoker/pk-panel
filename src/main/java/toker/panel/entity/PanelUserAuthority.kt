package toker.panel.entity

import javax.persistence.*

@Entity
@Table(name = "panel_user_authority")
class PanelUserAuthority(
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        @Column(name = "id", nullable = false)
        var id: Int? = null,

        @Column(name = "authority_name", nullable = false, unique = true, length = 64)
        var authorityName: String? = null,

        @Column(name = "authority_description", nullable = false, length = 128)
        var authorityDescription: String? = null
)