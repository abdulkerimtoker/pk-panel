package toker.panel.entity

import com.fasterxml.jackson.annotation.JsonView
import toker.panel.entity.pk.AdminPermissionsPK
import javax.persistence.*

@Entity
@Table(name = "admin_permissions")
@IdClass(AdminPermissionsPK::class)
class AdminPermissions(
        @Id
        @Column(name = "unique_id", nullable = false)
        var uniqueId: Int? = null,

        @Id
        @ManyToOne
        @JoinColumn(name = "server_id", referencedColumnName = "id", nullable = false)
        @JsonView(View.Server::class)
        var server: Server? = null,
        
        @Column(name = "panel", nullable = false)
        var panel: Boolean? = false,

        @Column(name = "gold", nullable = false)
        var gold: Boolean? = false,

        @Column(name = "kick", nullable = false)
        var kick: Boolean? = false,

        @Column(name = "temporary_ban", nullable = false)
        var temporaryBan: Boolean? = false,

        @Column(name = "permanent_ban", nullable = false)
        var permanentBan: Boolean? = false,

        @Column(name = "kill_fade", nullable = false)
        var killFade: Boolean? = false,

        @Column(name = "can_freeze", nullable = false)
        var freeze: Boolean? = false,

        @Column(name = "teleport_self", nullable = false)
        var teleportSelf: Boolean? = false,

        @Column(name = "admin_items", nullable = false)
        var adminItems: Boolean? = false,

        @Column(name = "heal_self", nullable = false)
        var healSelf: Boolean? = false,

        @Column(name = "godlike_troop", nullable = false)
        var godlikeTroop: Boolean? = false,

        @Column(name = "ships", nullable = false)
        var ships: Boolean? = false,

        @Column(name = "announce", nullable = false)
        var announce: Boolean? = false,

        @Column(name = "override_poll", nullable = false)
        var overridePoll: Boolean? = false,

        @Column(name = "all_items", nullable = false)
        var allItems: Boolean? = false,

        @Column(name = "mute", nullable = false)
        var mute: Boolean? = false,

        @Column(name = "animals", nullable = false)
        var animals: Boolean? = false,

        @Column(name = "join_factions", nullable = false)
        var joinFactions: Boolean? = false,

        @Column(name = "factions", nullable = false)
        var factions: Boolean? = false
) {
        override fun equals(other: Any?): Boolean {
                if (this === other) return true
                if (javaClass != other?.javaClass) return false
                other as AdminPermissions
                if (uniqueId != other.uniqueId) return false
                if (server != other.server) return false
                return true
        }

        override fun hashCode(): Int {
                var result = uniqueId ?: 0
                result = 31 * result + (server?.hashCode() ?: 0)
                return result
        }

        interface View {
                interface None
                interface Server
        }
}