package toker.panel.entity

import com.fasterxml.jackson.annotation.JsonView
import javax.persistence.*

@Entity
class AdminInvitation(
        @Id
        @Column(name = "code", nullable = false, unique = true)
        var code: String,

        @Column(name = "is_used", nullable = false)
        var isUsed: Boolean = false,

        @ManyToOne
        @JoinColumn(name = "inviter_id", referencedColumnName = "id")
        @JsonView(View.Inviter::class)
        var inviter: PanelUser,

        @ManyToOne
        @JoinColumn(name = "server_id", referencedColumnName = "id")
        @JsonView(View.Server::class)
        var server: Server
) {
        interface View {
                interface Inviter
                interface Server
                interface None
        }

        override fun equals(other: Any?): Boolean {
                if (this === other) return true
                if (javaClass != other?.javaClass) return false
                other as AdminInvitation
                if (code != other.code) return false
                if (server != other.server) return false
                return true
        }

        override fun hashCode(): Int {
                var result = code.hashCode()
                result = 31 * result + server.hashCode()
                return result
        }
}