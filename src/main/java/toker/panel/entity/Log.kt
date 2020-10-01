package toker.panel.entity

import java.sql.Timestamp
import java.time.Instant
import javax.persistence.*

@Entity
@Table(name = "log")
class Log(
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        @Column(name = "id")
        var id: Int? = null,

        @Column(name = "target_id")
        var targetId: Int? = null,

        @ManyToOne
        @JoinColumn(name = "user_id", referencedColumnName = "id", nullable = false)
        var user: PanelUser? = null,

        @Column(name = "type", nullable = false)
        @Enumerated(EnumType.ORDINAL)
        var type: Type? = null,

        @Column(name = "time", nullable = false)
        var time: Timestamp? = Timestamp.from(Instant.now()),

        @Column(name = "data", length = 4096)
        var data: String? = null
) {
    enum class Type {
        LOG_FETCHING
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false
        other as Log
        if (id != other.id) return false
        return true
    }

    override fun hashCode(): Int {
        return id ?: 0
    }
}