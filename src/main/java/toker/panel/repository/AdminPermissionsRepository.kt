package toker.panel.repository;

import toker.panel.entity.AdminPermissions;
import toker.panel.entity.pk.AdminPermissionsPK;

import java.util.Optional;

public interface AdminPermissionsRepository extends BaseRepository<AdminPermissions, AdminPermissionsPK> {
    Optional<AdminPermissions> findByServerIdAndUniqueId(Integer serverId, Integer uniqueId);
}
