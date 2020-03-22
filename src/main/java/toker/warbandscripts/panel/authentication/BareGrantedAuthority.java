package toker.warbandscripts.panel.authentication;

import org.springframework.security.core.GrantedAuthority;

public class BareGrantedAuthority implements GrantedAuthority {
    private String authority;

    public BareGrantedAuthority(String authority) {
        this.authority = authority;
    }

    @Override
    public String getAuthority() {
        return authority;
    }
}
