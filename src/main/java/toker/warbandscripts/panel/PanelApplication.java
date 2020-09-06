package toker.warbandscripts.panel;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import toker.warbandscripts.panel.authentication.EndedSessions;
import toker.warbandscripts.panel.entity.PanelUserSession;
import toker.warbandscripts.panel.entity.PanelUserSession_;
import toker.warbandscripts.panel.repository.BaseRepository;

import java.io.File;
import java.io.FileInputStream;
import java.util.List;
import java.util.Properties;
import java.util.stream.Collectors;

@SpringBootApplication
@EnableJpaRepositories
@EnableAspectJAutoProxy
@EnableScheduling
public class PanelApplication implements CommandLineRunner {

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

        String[] compulsoryConfigs = {
                "PANEL_DB_HOST", "PANEL_DB_PORT",
                "PANEL_DB_NAME", "PANEL_DB_USER",
                "PANEL_DB_USER_PASS"
        };

        for (String config : compulsoryConfigs) {
            if (!properties.containsKey(config)) {
                System.out.println(String.format("Config option %s is required for the panel to start. Please edit config.txt and make sure its value is set."));
                return;
            }
        }

        SpringApplication.run(PanelApplication.class, args);
    }

    private BaseRepository<PanelUserSession, Integer> sessionRepo;

    public PanelApplication(BaseRepository<PanelUserSession, Integer> sessionRepo) {
        this.sessionRepo = sessionRepo;
    }

    @Override
    public void run(String... args) {
        List<PanelUserSession> endedSessions = sessionRepo.findAll((root, query, builder) ->
                builder.equal(root.get(PanelUserSession_.ended), true));
        EndedSessions.endSessions(endedSessions.stream().map(PanelUserSession::getId).collect(Collectors.toList()));
    }

    @Scheduled(fixedDelay = 5 * 1000)
    public void endSessionsPeriodically() {
        List<PanelUserSession> endedSessions = sessionRepo.findAll((root, query, builder) ->
                builder.equal(root.get(PanelUserSession_.ended), true));
        EndedSessions.endSessions(endedSessions.stream().map(PanelUserSession::getId).collect(Collectors.toList()));
    }
}
