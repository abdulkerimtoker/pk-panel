package toker.warbandscripts.panel.bean;

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
