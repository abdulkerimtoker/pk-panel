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
}