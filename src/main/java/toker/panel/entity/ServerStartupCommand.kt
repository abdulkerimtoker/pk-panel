package toker.panel.entity

import com.fasterxml.jackson.annotation.JsonView
import toker.panel.entity.pk.ServerStartupCommandPK
import javax.persistence.*

@Entity
@Table(name = "server_startup_command")
@IdClass(ServerStartupCommandPK::class)
class ServerStartupCommand(
        @Id
        @ManyToOne
        @JoinColumn(name = "server_id", referencedColumnName = "id")
        @JsonView(View.Server::class)
        var server: Server? = null,

        @Id
        @Column(name = "command", length = 64, nullable = false)
        var command: String? = null,

        @Column(name = "value")
        var value: String? = null,

        @Column(name = "_order", nullable = false)
        var order: Int? = null
) {
    interface View {
        interface None
        interface Server
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false
        other as ServerStartupCommand
        if (server != other.server) return false
        if (command != other.command) return false
        return true
    }

    override fun hashCode(): Int {
        var result = server?.hashCode() ?: 0
        result = 31 * result + (command?.hashCode() ?: 0)
        return result
    }
}