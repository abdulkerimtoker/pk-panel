package toker.panel.config;

import org.apache.commons.dbcp2.BasicDataSource;
import org.hibernate.jpa.HibernatePersistenceProvider;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;
import org.springframework.orm.hibernate5.LocalSessionFactoryBean;
import org.springframework.orm.jpa.JpaDialect;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaDialect;
import toker.panel.service.PanelConfigurationService;

import javax.sql.DataSource;
import java.util.Properties;

@Configuration
public class DataSourceConfig {

    private PanelConfigurationService configurationService;

    public DataSourceConfig(PanelConfigurationService configurationService) {
        this.configurationService = configurationService;
    }

    @Bean
    @Qualifier("dataSource")
    public DataSource dataSource() {
        BasicDataSource dataSource = new BasicDataSource();
        dataSource.setDriverClassName("org.postgresql.Driver");
        dataSource.setUrl(String.format("jdbc:postgresql://%s:%s/%s",
                configurationService.getProperty("PANEL_DB_HOST"),
                configurationService.getProperty("PANEL_DB_PORT"),
                configurationService.getProperty("PANEL_DB_NAME")));
        dataSource.setUsername(configurationService.getProperty("PANEL_DB_USER"));
        dataSource.setPassword(configurationService.getProperty("PANEL_DB_USER_PASS"));
        dataSource.setInitialSize(4);
        return dataSource;
    }
}
