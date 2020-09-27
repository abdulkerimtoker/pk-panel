package toker.panel.repository

import toker.panel.entity.AdminPermissions
import toker.panel.entity.pk.AdminPermissionsPK
import java.util.*

interface AdminPermissionsRepository : BaseRepository<AdminPermissions, AdminPermissionsPK> {
    fun findByServerIdAndUniqueId(serverId: Int, uniqueId: Int): Optional<AdminPermissions>
}