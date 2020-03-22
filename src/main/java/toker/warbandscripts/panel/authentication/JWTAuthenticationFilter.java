package toker.warbandscripts.panel.authentication;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

public class JWTAuthenticationFilter extends BasicAuthenticationFilter {

    public JWTAuthenticationFilter(AuthenticationManager authenticationManager) {
        super(authenticationManager);
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws IOException, ServletException {
        String jwt = request.getHeader("Authorization");

        if (jwt != null && jwt.startsWith("Bearer ")) {
            jwt = jwt.replace("Bearer ", "");
            try {
                DecodedJWT decodedJWT = JWT.require(Algorithm.HMAC512("sea".getBytes()))
                        .build()
                        .verify(jwt);

                String username = decodedJWT.getSubject();
                String claimedIdentity = decodedJWT.getClaim("Identity").asString();

                List<BareGrantedAuthority> authorityList = decodedJWT.getClaim("Authorizations")
                        .asList(String.class)
                        .stream()
                        .map(BareGrantedAuthority::new)
                        .collect(Collectors.toList());

                SecurityContextHolder.getContext().setAuthentication(
                        new JWTOpenIDAuthenticationToken(authorityList, username, jwt, claimedIdentity));
            }
            catch (JWTVerificationException ignored) {}
        }

        chain.doFilter(request, response);
    }

    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) {
        return request.getServletPath().equals("/api/login") ||
                request.getServletPath().equals("/api/processLogin");
    }
}