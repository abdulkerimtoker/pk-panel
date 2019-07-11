package toker.warbandscripts.panel.config;

import org.apache.commons.dbcp2.BasicDataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.orm.jpa.JpaVendorAdapter;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.Database;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import toker.warbandscripts.panel.service.PanelConfigurationService;

import javax.sql.DataSource;

@Configuration
public class JPAConfiguration {

    private PanelConfigurationService configurationService;

    @Autowired
    public JPAConfiguration(PanelConfigurationService configurationService) {
        this.configurationService = configurationService;
    }

    @Bean
    @Qualifier("dataSource")
    public BasicDataSource dataSource() {
        BasicDataSource dataSource = new BasicDataSource();
        dataSource.setDriverClassName("com.mysql.cj.jdbc.Driver");
        dataSource.setUrl(String.format("jdbc:mysql://%s:%s/%s?zeroDateTimeBehavior=convertToNull",
                configurationService.getProperty("PANEL_DB_HOST"),
                configurationService.getProperty("PANEL_DB_PORT"),
                configurationService.getProperty("PANEL_DB_NAME")));
        dataSource.setUsername(configurationService.getProperty("PANEL_DB_USER"));
        dataSource.setPassword(configurationService.getProperty("PANEL_DB_USER_PASS"));
        dataSource.setInitialSize(4);
        return dataSource;
    }

    @Bean
    public LocalContainerEntityManagerFactoryBean entityManagerFactory (
            @Qualifier("dataSource") DataSource dataSource,
            JpaVendorAdapter jpaVendorAdapter) {
        LocalContainerEntityManagerFactoryBean emfb =
                new LocalContainerEntityManagerFactoryBean();
        emfb.setDataSource(dataSource);
        emfb.setJpaVendorAdapter(jpaVendorAdapter);
        emfb.setPackagesToScan("toker.warbandscripts.panel.entity");
        return emfb;
    }

    @Bean
    public JpaVendorAdapter jpaVendorAdapter() {
        HibernateJpaVendorAdapter adapter = new HibernateJpaVendorAdapter();
        adapter.setDatabase(Database.MYSQL);
        adapter.setDatabasePlatform("org.hibernate.dialect.MySQL57Dialect");
        adapter.setShowSql(false);
        adapter.setGenerateDdl(false);
        return adapter;
    }
}
