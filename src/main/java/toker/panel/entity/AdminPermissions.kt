package toker.panel.entity

import com.fasterxml.jackson.annotation.JsonView
import toker.panel.annotation.DefaultBooleanValue
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
        
        @Column(name = "panel", nullable = true)
        @get:DefaultBooleanValue(false)
        var panel: Boolean? = null,

        @Column(name = "gold", nullable = true)
        @get:DefaultBooleanValue(false)
        var gold: Boolean? = null,

        @Column(name = "kick", nullable = true)
        @get:DefaultBooleanValue(false)
        var kick: Boolean? = null,

        @Column(name = "temporary_ban", nullable = true)
        @get:DefaultBooleanValue(false)
        var temporaryBan: Boolean? = null,

        @Column(name = "permanent_ban", nullable = true)
        @get:DefaultBooleanValue(false)
        var permanentBan: Boolean? = null,

        @Column(name = "kill_fade", nullable = true)
        @get:DefaultBooleanValue(false)
        var killFade: Boolean? = null,

        @Column(name = "can_freeze", nullable = true)
        @get:DefaultBooleanValue(false)
        var freeze: Boolean? = null,

        @Column(name = "teleport_self", nullable = true)
        @get:DefaultBooleanValue(false)
        var teleportSelf: Boolean? = null,

        @Column(name = "admin_items", nullable = true)
        @get:DefaultBooleanValue(false)
        var adminItems: Boolean? = null,

        @Column(name = "heal_self", nullable = true)
        @get:DefaultBooleanValue(false)
        var healSelf: Boolean? = null,

        @Column(name = "godlike_troop", nullable = true)
        @get:DefaultBooleanValue(false)
        var godlikeTroop: Boolean? = null,

        @Column(name = "ships", nullable = true)
        @get:DefaultBooleanValue(false)
        var ships: Boolean? = null,

        @Column(name = "announce", nullable = true)
        @get:DefaultBooleanValue(false)
        var announce: Boolean? = null,

        @Column(name = "override_poll", nullable = true)
        @get:DefaultBooleanValue(false)
        var overridePoll: Boolean? = null,

        @Column(name = "all_items", nullable = true)
        @get:DefaultBooleanValue(false)
        var allItems: Boolean? = null,

        @Column(name = "mute", nullable = true)
        @get:DefaultBooleanValue(false)
        var mute: Boolean? = null,

        @Column(name = "animals", nullable = true)
        @get:DefaultBooleanValue(false)
        var animals: Boolean? = null,

        @Column(name = "join_factions", nullable = true)
        @get:DefaultBooleanValue(false)
        var joinFactions: Boolean? = null,

        @Column(name = "factions", nullable = true)
        @get:DefaultBooleanValue(false)
        var factions: Boolean? = null
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