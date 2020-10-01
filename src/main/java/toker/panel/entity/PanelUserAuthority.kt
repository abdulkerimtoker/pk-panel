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
) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false
        other as PanelUserAuthority
        if (id != other.id) return false
        return true
    }

    override fun hashCode(): Int {
        return id ?: 0
    }
}