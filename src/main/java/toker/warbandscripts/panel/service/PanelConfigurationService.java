package toker.warbandscripts.panel.service;

import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.Properties;

@Service
public class PanelConfigurationService {

    private Properties properties;

    public PanelConfigurationService() throws Exception {
        properties = new Properties();
        File conf = new File(new File(System.getProperty("user.dir")), "config.txt");
        FileInputStream inputStream = new FileInputStream(conf);
        properties.load(inputStream);
        inputStream.close();
    }

    public String getProperty(String key) {
        return properties.getProperty(key);
    }
}
