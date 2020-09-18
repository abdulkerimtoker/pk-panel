package toker.panel.authentication;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import toker.panel.entity.Server;
import toker.panel.service.ServerService;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class GameAPIAuthenticationFilter extends BasicAuthenticationFilter {

    private ServerService serverService;

    public GameAPIAuthenticationFilter(AuthenticationManager authenticationManager,
                                       ServerService serverService) {
        super(authenticationManager);
        this.serverService = serverService;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain chain) throws IOException, ServletException {
        String serverKey = request.getParameter("authkey");
        if (serverKey != null) {
            Server server = serverService.getServerByKey(serverKey);
            if (server != null) {
                SecurityContextHolder.getContext().setAuthentication(new GameAPIAuthentication(server));
            }
        }
        chain.doFilter(request, response);
    }

    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) throws ServletException {
        return !request.getServletPath().startsWith("/gameapi");
    }
}
