package toker.warbandscripts.panel.authentication;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import toker.warbandscripts.panel.entity.PanelUser;
import toker.warbandscripts.panel.entity.PanelUserAuthority;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class PanelUserDetails implements UserDetails {

    private PanelUser panelUser;
    private List<PanelUserAuthority> authorities;

    public PanelUserDetails(PanelUser panelUser) {
        this.panelUser = panelUser;
        this.authorities = new ArrayList<>();
        this.panelUser.getPanelUserAuthorityAssignmentsById()
                .forEach(panelUserAuthorityAssignment -> this.authorities.add(panelUserAuthorityAssignment.getPanelUserAuthorityByAuthorityId()));
    }

    public PanelUser getPanelUser() {
        return panelUser;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return this.authorities;
    }

    @Override
    public String getPassword() {
        return panelUser.getPassword();
    }

    @Override
    public String getUsername() {
        return panelUser.getUsername();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return !this.authorities.isEmpty();
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return !panelUser.getIsLocked();
    }
}
