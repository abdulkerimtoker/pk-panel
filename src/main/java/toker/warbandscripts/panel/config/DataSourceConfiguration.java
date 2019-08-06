package toker.warbandscripts.panel.config;

import org.apache.commons.dbcp2.BasicDataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import toker.warbandscripts.panel.service.PanelConfigurationService;

@Configuration
public class DataSourceConfiguration {

    @Autowired
    private PanelConfigurationService configurationService;

    public DataSourceConfiguration(PanelConfigurationService configurationService) {
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
}