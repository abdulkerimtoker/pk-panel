package toker.panel.authentication;

import org.springframework.security.core.GrantedAuthority;

import java.util.Objects;

public class BareGrantedAuthority implements GrantedAuthority {

    private String authority;

    public BareGrantedAuthority(String authority) {
        this.authority = authority;
    }

    @Override
    public String getAuthority() {
        return authority;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        BareGrantedAuthority that = (BareGrantedAuthority) o;
        return Objects.equals(authority, that.authority);
    }
}
