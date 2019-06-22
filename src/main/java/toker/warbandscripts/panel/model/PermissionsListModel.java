package toker.warbandscripts.panel.model;

import toker.warbandscripts.panel.entity.AdminPermissions;

import java.util.List;

public class PermissionsListModel {
    private List<AdminPermissions> permissions;

    public List<AdminPermissions> getPermissions() {
        return permissions;
    }

    public void setPermissions(List<AdminPermissions> permissions) {
        this.permissions = permissions;
    }
}
