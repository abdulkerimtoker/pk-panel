package toker.panel.entity

import javax.persistence.*

@Entity
@Table(name = "ip_record")
class IpRecord(
        @Id
        @GeneratedValue(strategy = GenerationType.AUTO)
        @Column(name = "id")
        var id: Int? = null,

        @Column(name = "unique_id", nullable = false)
        var uniqueId: Int? = null,

        @Column(name = "ip_address", nullable = false, length = 16)
        var ipAddress: String? = null,

        @ManyToOne
        @JoinColumn(name = "server_id", referencedColumnName = "id", nullable = false)
        var server: Server? = null
) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false
        other as IpRecord
        if (id != other.id) return false
        return true
    }

    override fun hashCode(): Int {
        return id ?: 0
    }
}