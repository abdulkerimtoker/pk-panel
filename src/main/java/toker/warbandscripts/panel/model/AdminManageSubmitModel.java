package toker.warbandscripts.panel.model;

import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

public class AdminManageSubmitModel {
    private int adminId;
    @Size(min = 3, max = 32)
    @Pattern(regexp = "^[A-Za-z0-9_-]+")
    private String username;
    private int rankId;
    @Pattern(regexp = "^[0-9,]+")
    private String authorities;

    public int getAdminId() {
        return adminId;
    }

    public void setAdminId(int adminId) {
        this.adminId = adminId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public int getRankId() {
        return rankId;
    }

    public void setRankId(int rankId) {
        this.rankId = rankId;
    }

    public String getAuthorities() {
        return authorities;
    }

    public void setAuthorities(String authorities) {
        this.authorities = authorities;
    }

}
