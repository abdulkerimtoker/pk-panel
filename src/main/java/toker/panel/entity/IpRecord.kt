package toker.panel.entity

import javax.persistence.*

@Entity
@Table(name = "ip_record")
data class IpRecord(
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
)