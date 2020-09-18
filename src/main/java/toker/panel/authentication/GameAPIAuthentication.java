package toker.panel.authentication;

import org.springframework.security.authentication.AbstractAuthenticationToken;
import toker.panel.entity.Server;

public class GameAPIAuthentication extends AbstractAuthenticationToken {

    private Server server;

    public GameAPIAuthentication(Server server) {
        super(null);
        setAuthenticated(true);
        this.server = server;
    }

    @Override
    public Object getCredentials() {
        return server.getKey();
    }

    @Override
    public Object getPrincipal() {
        return server;
    }
}
