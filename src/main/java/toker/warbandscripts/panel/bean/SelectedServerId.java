package toker.warbandscripts.panel.bean;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.security.core.context.SecurityContextHolder;
import toker.warbandscripts.panel.authentication.JWTOpenIDAuthenticationToken;

public class SelectedServerId {

    public static Integer get() {
        JWTOpenIDAuthenticationToken token =
                (JWTOpenIDAuthenticationToken) SecurityContextHolder
                        .getContext().getAuthentication();
        return token.getSelectedServerId();
    }
}
