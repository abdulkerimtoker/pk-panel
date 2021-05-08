package toker.panel.entity

import com.fasterxml.jackson.annotation.JsonIgnore
import com.fasterxml.jackson.annotation.JsonView
import toker.panel.entity.Server.View.StartupCommands
import javax.persistence.*

@Entity
@Table(name = "server")
class Server(
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        @Column(name = "id", nullable = false)
        var id: Int? = null,

        @Column(name = "name", nullable = false, length = 128)
        var name: String? = null,

        @Column(name = "port", nullable = false)
        var port: Int? = null,

        @Column(name = "key", nullable = false, length = 32)
        @JsonIgnore
        var key: String? = null,

        @Column(name = "exe_path")
        @JsonIgnore
        var exePath: String? = null,

        @Column(name = "wse_path")
        @JsonIgnore
        var wsePath: String? = null,

        @Column(name = "frond_cmd")
        @JsonIgnore
        var frontCmd: String? = null,

        @Column(name = "use_screen")
        @JsonIgnore
        var useScreen: Boolean? = null,

        @Column(name = "module_name", nullable = false)
        var moduleName: String? = null,
) {
    @OneToMany(mappedBy = "server")
    @JsonView(StartupCommands::class)
    var startupCommands: MutableSet<ServerStartupCommand>? = null

    interface View {
        interface None
        interface StartupCommands
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false
        other as Server
        if (id != other.id) return false
        return true
    }

    override fun hashCode(): Int {
        return id ?: 0
    }
}