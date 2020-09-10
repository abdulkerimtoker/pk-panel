package toker.warbandscripts.panel.repository;

import toker.warbandscripts.panel.entity.AdminPermissions;
import toker.warbandscripts.panel.entity.pk.AdminPermissionsPK;

import java.util.Optional;

public interface AdminPermissionsRepository extends BaseRepository<AdminPermissions, AdminPermissionsPK> {
    Optional<AdminPermissions> findByServerIdAndUniqueId(Integer serverId, Integer uniqueId);
}
