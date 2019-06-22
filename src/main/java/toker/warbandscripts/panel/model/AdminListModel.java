package toker.warbandscripts.panel.model;

import toker.warbandscripts.panel.entity.PanelUser;

import java.util.List;

public class AdminListModel {
    private List<PanelUser> admins;

    public List<PanelUser> getAdmins() {
        return admins;
    }

    public void setAdmins(List<PanelUser> admins) {
        this.admins = admins;
    }
}
