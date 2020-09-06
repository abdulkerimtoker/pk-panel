package toker.warbandscripts.panel.controller.gameapi;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import toker.warbandscripts.panel.entity.Faction;
import toker.warbandscripts.panel.entity.Server;
import toker.warbandscripts.panel.entity.ServerConfiguration;
import toker.warbandscripts.panel.repository.FactionRepository;
import toker.warbandscripts.panel.service.ServerService;

import java.util.Collection;
import java.util.Comparator;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
public class ServerGameController {

    private FactionRepository factionRepository;
    private ServerService serverService;

    public ServerGameController(FactionRepository factionRepository,
                                ServerService serverService) {
        this.factionRepository = factionRepository;
        this.serverService = serverService;
    }

    @GetMapping("/gameapi/loadfactions")
    public String loadFactions() {
        Server server = (Server) SecurityContextHolder.getContext()
                .getAuthentication().getPrincipal();
        Collection<Faction> factions = factionRepository.findAllByServerId(server.getId());
        StringBuilder sb = new StringBuilder(String.valueOf(factions.size()));

        factions.stream()
                .sorted(Comparator.comparingInt(Faction::getIndex))
                .forEach(faction -> {
                    sb.append('|');
                    sb.append(faction.getName());
                    sb.append('|');
                    sb.append(faction.getBannerId());
                });

        return sb.toString();
    }

    @GetMapping("/gameapi/loadserverconf")
    public String loadServerConf() {
        Server server = (Server) SecurityContextHolder.getContext()
                .getAuthentication().getPrincipal();
        Map<String, String> confMap = serverService.getServerConfigurations(server.getId())
                .stream()
                .collect(Collectors.toMap(ServerConfiguration::getName,
                        ServerConfiguration::getValue));

        return String.format("12345|%s|%s|%s|%s|%s|%s",
                confMap.getOrDefault("CONF_GOLD_PER_TICK", "0"),
                confMap.getOrDefault("CONF_FATIGUE_DECREASE_PER_TICK", "50"),
                confMap.getOrDefault("CONF_FATIGUE_DECREASE_PER_TICK_ARMORED", "100"),
                confMap.getOrDefault("CONF_FATIGUE_REGENERATION", "100"),
                confMap.getOrDefault("CONF_FATIGUE_RUN_AGAIN_POINT", "200"),
                confMap.getOrDefault("CONF_FATIGUE_ARMORED_STARTING_VALUE", "35"));
    }
}
