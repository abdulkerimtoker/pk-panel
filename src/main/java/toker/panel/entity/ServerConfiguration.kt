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
) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false
        other as ServerConfiguration
        if (server != other.server) return false
        if (name != other.name) return false
        return true
    }

    override fun hashCode(): Int {
        var result = server?.hashCode() ?: 0
        result = 31 * result + (name?.hashCode() ?: 0)
        return result
    }
}
