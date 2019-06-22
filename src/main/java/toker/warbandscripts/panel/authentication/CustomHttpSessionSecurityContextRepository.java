package toker.warbandscripts.panel.authentication;

import org.hibernate.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetails;
import org.springframework.security.web.context.HttpRequestResponseHolder;
import org.springframework.security.web.context.SecurityContextRepository;
import org.springframework.stereotype.Component;
import toker.warbandscripts.panel.service.PanelUserService;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Component
public class CustomHttpSessionSecurityContextRepository implements SecurityContextRepository {

    private PanelUserService panelUserService;

    @Autowired
    public CustomHttpSessionSecurityContextRepository(PanelUserService panelUserService) {
        this.panelUserService = panelUserService;
    }

    @Override
    public SecurityContext loadContext(HttpRequestResponseHolder requestResponseHolder) {
        if (requestResponseHolder.getRequest().getSession() != null) {
            Object session = requestResponseHolder.getRequest().getSession().getAttribute("SPRING_SECURITY_SESSION");

            if (session != null && session instanceof SecurityContext) {
                SecurityContext securityContext = (SecurityContext)session;
                Authentication authentication = securityContext.getAuthentication();

                if (authentication != null && authentication.getPrincipal() != null) {
                    PanelUserDetails panelUserDetails = (PanelUserDetails)authentication.getPrincipal();
                    if (panelUserService.isUserToBeLoggedOut(panelUserDetails.getPanelUser())) {
                        authentication.setAuthenticated(false);
                    }
                }
                return securityContext;
            }
        }
        return SecurityContextHolder.createEmptyContext();
    }

    @Override
    public void saveContext(SecurityContext context, HttpServletRequest request, HttpServletResponse response) {
        if (request.getSession() != null) {
            request.getSession().setAttribute("SPRING_SECURITY_SESSION", context);
        }
    }

    @Override
    public boolean containsContext(HttpServletRequest request) {
        if (request.getSession() != null) {
            Object session = request.getSession().getAttribute("SPRING_SECURITY_SESSION");
            return session != null && session instanceof SecurityContext;
        }
        return false;
    }
}
