package toker.warbandscripts.panel;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.Properties;

@SpringBootApplication
@EnableJpaRepositories
public class PanelApplication {

    public static void main(String[] args) throws Exception {
        Properties properties = new Properties();
        File conf = new File(new File(System.getProperty("user.dir")), "config.txt");
        FileInputStream inputStream = new FileInputStream(conf);
        properties.load(inputStream);
        inputStream.close();

        if (properties.containsKey("server.port")) {
            System.setProperty("server.port", properties.getProperty("server.port"));
        } else {
            System.setProperty("server.port", "8080");
        }

        String[] compulsoryConfigs = {"PANEL_DB_HOST", "PANEL_DB_PORT", "PANEL_DB_NAME", "PANEL_DB_USER",
                "PANEL_DB_USER_PASS", "SERVER_BAN_LIST_FILE", "SERVER_LOGS_DIR", "SERVER_STARTUP_COMMAND",
                "SERVER_NAME", "SERVER_HOST", "SERVER_PORT", "PANEL_TITLE"};

        for (String config : compulsoryConfigs) {
            if (!properties.containsKey(config)) {
                System.out.println(String.format("Config option %s is required for the panel to start. Please edit config.txt and make sure its value is set."));
                return;
            }
        }

        System.setProperty("PANEL_TITLE", properties.getProperty("PANEL_TITLE"));

        SpringApplication.run(PanelApplication.class, args);
    }
}
