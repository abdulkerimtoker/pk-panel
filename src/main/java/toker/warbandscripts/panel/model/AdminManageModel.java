package toker.warbandscripts.panel.model;

import toker.warbandscripts.panel.entity.PanelUser;
import toker.warbandscripts.panel.entity.PanelUserAuthority;
import toker.warbandscripts.panel.entity.PanelUserAuthorityAssignment;
import toker.warbandscripts.panel.entity.PanelUserRank;

import java.util.Collection;
import java.util.List;

public class AdminManageModel {
    private PanelUser admin;
    private Collection<PanelUserRank> ranks;
    private Collection<PanelUserAuthority> authorities;

    public PanelUser getAdmin() {
        return admin;
    }

    public void setAdmin(PanelUser admin) {
        this.admin = admin;
    }

    public Collection<PanelUserRank> getRanks() {
        return ranks;
    }

    public void setRanks(Collection<PanelUserRank> ranks) {
        this.ranks = ranks;
    }

    public Collection<PanelUserAuthority> getAuthorities() {
        return authorities;
    }

    public void setAuthorities(Collection<PanelUserAuthority> authorities) {
        this.authorities = authorities;
    }
}
