package toker.panel.entity

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
        var server: Server? = null,

        @Column(name = "panel", nullable = true)
        var panel: Boolean? = null,

        @Column(name = "gold", nullable = true)
        var gold: Boolean? = null,

        @Column(name = "kick", nullable = true)
        var kick: Boolean? = null,

        @Column(name = "temporary_ban", nullable = true)
        var temporaryBan: Boolean? = null,

        @Column(name = "permanent_ban", nullable = true)
        var permanentBan: Boolean? = null,

        @Column(name = "kill_fade", nullable = true)
        var killFade: Boolean? = null,

        @Column(name = "can_freeze", nullable = true)
        var freeze: Boolean? = null,

        @Column(name = "teleport_self", nullable = true)
        var teleportSelf: Boolean? = null,

        @Column(name = "admin_items", nullable = true)
        var adminItems: Boolean? = null,

        @Column(name = "heal_self", nullable = true)
        var healSelf: Boolean? = null,

        @Column(name = "godlike_troop", nullable = true)
        var godlikeTroop: Boolean? = null,

        @Column(name = "ships", nullable = true)
        var ships: Boolean? = null,

        @Column(name = "announce", nullable = true)
        var announce: Boolean? = null,

        @Column(name = "override_poll", nullable = true)
        var overridePoll: Boolean? = null,

        @Column(name = "all_items", nullable = true)
        var allItems: Boolean? = null,

        @Column(name = "mute", nullable = true)
        var mute: Boolean? = null,

        @Column(name = "animals", nullable = true)
        var animals: Boolean? = null,

        @Column(name = "join_factions", nullable = true)
        var joinFactions: Boolean? = null,

        @Column(name = "factions", nullable = true)
        var factions: Boolean? = null
)