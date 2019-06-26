package toker.warbandscripts.panel.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.*;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.authentication.jaas.JaasAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.context.SecurityContextImpl;
import org.springframework.security.core.session.SessionRegistry;
import org.springframework.security.core.session.SessionRegistryImpl;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.access.intercept.FilterSecurityInterceptor;
import org.springframework.security.web.authentication.rememberme.InMemoryTokenRepositoryImpl;
import org.springframework.security.web.authentication.rememberme.PersistentTokenRepository;
import org.springframework.security.web.authentication.session.SessionAuthenticationException;
import org.springframework.security.web.authentication.session.SessionAuthenticationStrategy;
import org.springframework.security.web.context.HttpRequestResponseHolder;
import org.springframework.security.web.context.HttpSessionSecurityContextRepository;
import org.springframework.security.web.context.SecurityContextRepository;
import org.springframework.session.FindByIndexNameSessionRepository;
import org.springframework.session.Session;
import org.springframework.session.SessionRepository;
import org.springframework.session.security.SpringSessionBackedSessionRegistry;
import toker.warbandscripts.panel.authentication.CustomHttpSessionSecurityContextRepository;
import toker.warbandscripts.panel.authentication.NoPasswordEncoder;
import toker.warbandscripts.panel.authentication.PanelUserDetails;
import toker.warbandscripts.panel.authentication.PanelUserDetailsService;
import toker.warbandscripts.panel.entity.PanelUser;

import javax.imageio.spi.ServiceRegistry;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(securedEnabled = true)
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

    private PanelUserDetailsService userDetailsService;
    private CustomHttpSessionSecurityContextRepository contextRepository;
    private PersistentTokenRepository remembermeTokenRepository;

    @Autowired
    public SecurityConfiguration(PanelUserDetailsService userDetailsService,
                                 CustomHttpSessionSecurityContextRepository contextRepository,
                                 PersistentTokenRepository remembermeTokenRepository) {
        this.userDetailsService = userDetailsService;
        this.contextRepository = contextRepository;
        this.remembermeTokenRepository = remembermeTokenRepository;
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.formLogin()
                .successForwardUrl("/home")
                .and()
                .rememberMe()
                .tokenRepository(this.remembermeTokenRepository)
                .and()
                .authorizeRequests()
                .anyRequest()
                .permitAll();
                //.authenticated()
                //.and()
                //.securityContext()
                //.securityContextRepository(this.contextRepository);
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService).passwordEncoder(new NoPasswordEncoder());
    }
}
