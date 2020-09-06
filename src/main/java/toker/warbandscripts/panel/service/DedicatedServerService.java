package toker.warbandscripts.panel.service;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import toker.warbandscripts.panel.entity.Server;
import toker.warbandscripts.panel.entity.ServerStartupCommand;
import toker.warbandscripts.panel.repository.ServerRepository;

import javax.annotation.PreDestroy;
import java.io.*;
import java.net.Socket;
import java.util.*;

@Service
public class DedicatedServerService {

    private Map<Integer, Process> processMap = new HashMap<>();

    private ServerRepository serverRepository;

    private SimpMessagingTemplate messagingTemplate;

    public DedicatedServerService(ServerRepository serverRepository,
                                  SimpMessagingTemplate messagingTemplate) {
        this.serverRepository = serverRepository;
        this.messagingTemplate = messagingTemplate;
    }

    public void startServer(Server server) throws IOException {
        if (isServerUp(server)) {
            shutdownServer(server);
        }

        File wseFile = new File(server.getWsePath());
        File exeFile = new File(server.getExePath());
        File serverFolder = new File(exeFile.getParent());

        File configFile = new File(serverFolder, "config.txt");
        FileWriter writer = new FileWriter(configFile);

        for (ServerStartupCommand cmd : server.getStartupCommands()) {
            writer.write(String.format("%s %s", cmd.getCommand(), cmd.getValue()));
            writer.write(System.lineSeparator());
        }

        writer.write("set_server_ban_list_file banlist.txt");
        writer.write(System.lineSeparator());
        writer.write("set_server_log_folder logs");
        writer.write(System.lineSeparator());
        writer.write(String.format("set_port %d", server.getPort()));
        writer.write(System.lineSeparator());
        writer.write("set_upload_limit 100000000");
        writer.write(System.lineSeparator());
        writer.write("start");
        writer.write(System.lineSeparator());

        writer.flush();
        writer.close();

        List<String> cmds = new LinkedList<>();

        if (server.getUseScreen() != null && server.getUseScreen()) {
            cmds.addAll(Arrays.asList(String.format("screen -d -m -S %s", server.getName()).split(" ")));
        }

        if (server.getFrontCmd() != null) {
            cmds.add(server.getFrontCmd());
        }

        cmds.add(server.getWsePath());
        cmds.add("-p");
        cmds.add(server.getExePath());
        cmds.add("-r");
        cmds.add("config.txt");
        cmds.add("-m");
        cmds.add(server.getModuleName());

        ProcessBuilder pb = new ProcessBuilder(cmds);
        processMap.put(server.getId(), pb.start());

        messagingTemplate.convertAndSend(
                String.format("/channel/%d/state", server.getId()),
                "starting_up");
    }

    public void shutdownServer(Server server) {
        Process process = processMap.getOrDefault(server.getId(), null);

        if (process != null) {
            if (process.isAlive()) {
                process.descendants().forEach(ProcessHandle::destroy);
                process.destroy();
            }
        }

        ProcessBuilder pb = new ProcessBuilder("screen", "-S", server.getName(), "-X", "quit");
        try {
            pb.start();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public boolean isServerUp(Server server) {
        if (server.getUseScreen() != null && server.getUseScreen()) {
            boolean up = false;
            try {
                Socket socket = new Socket("127.0.0.1", server.getPort());
                OutputStream outputStream = socket.getOutputStream();
                outputStream.write("test".getBytes());
                outputStream.flush();
                socket.close();
                up = true;
            } catch (IOException ignored) {}
            return up;
        }
        Process process = processMap.getOrDefault(server.getId(), null);
        return process != null && process.isAlive();
    }

    @PreDestroy
    private void shutdownAllServers() {
        for (Server server : serverRepository.findAll()) {
            if (isServerUp(server)) {
                shutdownServer(server);
            }
        }
    }

    @Scheduled(fixedDelay = 5000)
    private void broadcastServerStates() {
        for (Server server : serverRepository.findAll()) {
            Process process = processMap.getOrDefault(server.getId(), null);
            boolean up = server.getUseScreen() != null && server.getUseScreen() ?
                    isServerUp(server) : process != null && process.isAlive();
            messagingTemplate.convertAndSend(
                    String.format("/channel/%d/state", server.getId()),
                    up ? "online" : "offline");
        }
    }
}
