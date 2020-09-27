package toker.panel.entity

import toker.panel.entity.pk.ServerConfigurationPK
import java.util.*
import javax.persistence.*

@Entity
@Table(name = "server_configuration")
@IdClass(ServerConfigurationPK::class)
class ServerConfiguration(
        @Id
        @ManyToOne
        @JoinColumn(name = "server_id", referencedColumnName = "id", nullable = false)
        var server: Server? = null,

        @Id
        @Column(name = "name", nullable = false)
        var name: String? = null,

        @Column(name = "type")
        var type: Int? = null,

        @Column(name = "value")
        var value: String? = null
)