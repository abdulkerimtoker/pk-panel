package toker.warbandscripts.panel.config;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ImportResource;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.rememberme.InMemoryTokenRepositoryImpl;
import org.springframework.security.web.authentication.rememberme.PersistentTokenRepository;

import java.io.File;

@Configuration
@EnableScheduling
public class GeneralBeanConfiguration {

    @Bean
    public File localLogFolder() {
        File localLogFolder = new File(System.getProperty("user.dir"), "logs");
        if (!localLogFolder.exists()) {
            localLogFolder.mkdir();
        }
        return localLogFolder;
    }

    @Bean
    public Logger logger() {
        return Logger.getLogger("FILE");
    }

    @Bean
    @Qualifier("rememberMeTokenRepository")
    public PersistentTokenRepository rememberMeTokenRepository() {
        return new InMemoryTokenRepositoryImpl();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
