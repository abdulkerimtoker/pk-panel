package toker.warbandscripts.panel.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import javax.annotation.PreDestroy;
import java.io.*;
import java.net.Socket;

@Service
public class DedicatedServerService {

    private PanelConfigurationService configurationService;

    private boolean autoStart;

    @Autowired
    public DedicatedServerService(PanelConfigurationService configurationService) {
        this.configurationService = configurationService;
    }

    public boolean isServerUp() {
        String serverHost = configurationService.getProperty("SERVER_HOST");
        int serverPort = Integer.parseInt(configurationService.getProperty("SERVER_PORT"));

        try {
            Socket socket = new Socket(serverHost, serverPort);
            OutputStream os = socket.getOutputStream();

            os.write("test".getBytes());
            os.flush();

            os.close();
            socket.close();

            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public boolean startServer() {
        if (isServerUp()) {
            shutdownServer();
        }

        String command = configurationService.getProperty("SERVER_STARTUP_COMMAND");
        String serverName = configurationService.getProperty("SERVER_NAME");

        if (command != null && !command.isEmpty()) {
            try {
                ProcessBuilder processBuilder = new ProcessBuilder("screen", "-d", "-m", "-S", serverName, "sh", command);
                processBuilder.start();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        return true;
    }

    @PreDestroy
    public boolean shutdownServer() {
        String serverName = configurationService.getProperty("SERVER_NAME");
        try {
            ProcessBuilder processBuilder = new ProcessBuilder("screen", "-S", serverName, "-X", "quit");
            processBuilder.start();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return true;
    }

    public void setAutoStart(boolean autoStart) {
        this.autoStart = autoStart;
    }

    public boolean isAutoStart() {
        return autoStart;
    }

    @Scheduled(fixedDelay = 1000 * 30)
    public void autoStartCheck() {
        if (autoStart && !isServerUp()) {
            startServer();
        }
    }
}
