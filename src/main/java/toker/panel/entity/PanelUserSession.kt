package toker.panel.entity

import java.sql.Timestamp
import java.time.Instant
import javax.persistence.*

@Entity
@Table(name = "panel_user_session")
class PanelUserSession(
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        @Column(name = "id")
        var id: Int? = null,

        @ManyToOne
        @JoinColumn(name = "user_id", referencedColumnName = "id", nullable = false)
        var user: PanelUser? = null,

        @Column(name = "time", nullable = false)
        var time: Timestamp? = Timestamp.from(Instant.now()),

        @Column(name = "ended", nullable = false)
        var isEnded: Boolean? = false
) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false
        other as PanelUserSession
        if (id != other.id) return false
        return true
    }

    override fun hashCode(): Int {
        return id ?: 0
    }
}