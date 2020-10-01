package toker.panel.entity

import com.fasterxml.jackson.annotation.JsonView
import java.sql.Timestamp
import java.time.Instant
import java.util.*
import javax.persistence.*

@Entity
@Table(name = "ban")
class Ban(
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        @Column(name = "id", nullable = false)
        var id: Int? = null,

        @Column(name = "player_unique_id", nullable = false)
        var playerUniqueId: Int? = null,

        @Column(name = "time", nullable = false)
        var time: Timestamp? = null,

        @Column(name = "minutes", nullable = false)
        var minutes: Int? = null,

        @Column(name = "is_undone", nullable = false)
        var isUndone: Boolean? = null,

        @Column(name = "is_permanent", nullable = false)
        var isPermanent: Boolean? = null,

        @Column(name = "reason", nullable = false, length = 512)
        var reason: String? = null,

        @ManyToOne
        @JoinColumn(name = "admin_id", referencedColumnName = "id", nullable = false)
        @JsonView(View.PanelUser::class)
        var panelUser: PanelUser? = null,

        @ManyToOne
        @JoinColumn(name = "server_id", referencedColumnName = "id", nullable = false)
        @JsonView(View.Server::class)
        var server: Server? = null
) {
    @get:Transient
    val isExpired: Boolean
        get() {
            if (isPermanent!!) return false
            val calendar = Calendar.getInstance()
            calendar.time = time
            calendar.add(Calendar.MINUTE, minutes!!)
            return calendar.toInstant().isBefore(Instant.now())
        }

    interface View {
        interface PanelUser
        interface None
        interface Server
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false
        other as Ban
        if (id != other.id) return false
        return true
    }

    override fun hashCode(): Int {
        return id ?: 0
    }
}