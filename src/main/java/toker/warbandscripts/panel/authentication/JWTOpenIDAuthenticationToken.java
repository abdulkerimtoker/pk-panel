package toker.warbandscripts.panel.authentication;

import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;

import javax.security.auth.Subject;
import java.util.Collection;

public class JWTOpenIDAuthenticationToken extends AbstractAuthenticationToken {

    private Collection<? extends GrantedAuthority> authorities;

    private String username;
    private String jwt;
    private String claimedIdentity;

    public JWTOpenIDAuthenticationToken(Collection<? extends GrantedAuthority> authorities,
                                        String username,
                                        String jwt,
                                        String claimedIdentity) {
        super(authorities);
        this.authorities = authorities;
        this.username = username;
        this.jwt = jwt;
        this.claimedIdentity = claimedIdentity;
        setAuthenticated(true);
    }

    @Override
    public Object getCredentials() {
        return jwt;
    }

    @Override
    public Object getPrincipal() {
        return username;
    }

    @Override
    public Object getDetails() {
        return claimedIdentity;
    }
}