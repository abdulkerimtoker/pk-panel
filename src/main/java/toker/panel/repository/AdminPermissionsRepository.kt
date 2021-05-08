package toker.panel.repository

import org.springframework.data.jpa.repository.Modifying
import org.springframework.transaction.annotation.Transactional
import toker.panel.entity.AdminPermissions
import toker.panel.entity.pk.AdminPermissionsPK
import java.util.*

interface AdminPermissionsRepository : BaseRepository<AdminPermissions, AdminPermissionsPK> {
    fun findByServerIdAndUniqueId(serverId: Int, uniqueId: Int): Optional<AdminPermissions>
    fun findAllByServerId(serverId: Int): List<AdminPermissions>

    @Transactional
    @Modifying
    fun deleteByServerIdAndUniqueId(serverId: Int, uniqueId: Int): Int
}