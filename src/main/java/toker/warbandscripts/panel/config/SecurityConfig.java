package toker.warbandscripts.panel.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import toker.warbandscripts.panel.authentication.GameAPIAuthenticationFilter;
import toker.warbandscripts.panel.authentication.JWTAuthenticationFilter;
import toker.warbandscripts.panel.service.ServerService;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(
        securedEnabled = true,
        prePostEnabled = true
)
@Order(1)
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private ServerService serverService;

    public SecurityConfig(ServerService serverService) {
        this.serverService = serverService;
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.addFilter(new JWTAuthenticationFilter(authenticationManager()))
                .addFilter(new GameAPIAuthenticationFilter(authenticationManager(), serverService))
                .csrf()
                .disable()
                .authorizeRequests()
                .antMatchers("/api/login", "/api/processLogin",
                        "/ws/**", "/download/**")
                .permitAll()
                .anyRequest()
                .authenticated()
                .and()
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS);
    }
}