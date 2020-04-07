package toker.warbandscripts.panel.service;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import toker.warbandscripts.panel.entity.Ban;
import toker.warbandscripts.panel.entity.PanelUser;
import toker.warbandscripts.panel.entity.Player;
import toker.warbandscripts.panel.repository.BanRepository;
import toker.warbandscripts.panel.repository.PanelUserRepository;
import toker.warbandscripts.panel.repository.PlayerRepository;

import java.io.*;
import java.util.List;

@Service
public class BanService {

    private PanelConfigurationService configurationService;

    private BanRepository banRepository;
    private PlayerRepository playerRepository;
    private PanelUserRepository panelUserRepository;

    public BanService(PanelConfigurationService configurationService,
                      BanRepository banRepository,
                      PlayerRepository playerRepository,
                      PanelUserRepository panelUserRepository) {
        this.configurationService = configurationService;
        this.banRepository = banRepository;
        this.playerRepository = playerRepository;
        this.panelUserRepository = panelUserRepository;
    }

    @Scheduled(fixedDelay = 1000 * 60)
    public void refreshBanList() throws IOException {
        File file = new File(configurationService.getProperty("SERVER_BAN_LIST_FILE"));
        FileWriter fwriter = new FileWriter(file);
        BufferedWriter writer = new BufferedWriter(fwriter);

        //for (Ban ban : new LinkedHashSet<>(banRepository.getActiveBans())) {
        //    writer.write(String.valueOf(ban.getPlayerUniqueId()));
        //    writer.newLine();
        //}

        writer.flush();
        writer.close();
        fwriter.close();
    }

    public Ban banPlayer(Ban ban) {
        String adminName = (String)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        PanelUser admin = panelUserRepository.findByUsername(adminName);
        ban.setId(null);
        ban.setPanelUser(admin);
        return banRepository.save(ban);
    }

    public void undoBan(int banId) {
        banRepository.deleteById(banId);
    }

    public void undoAllBansForPlayer(int playerUniqueId) {
        banRepository.undoAllByPlayerUniqueId(playerUniqueId);
    }

    public List<Ban> getBansOfPlayer(int playerId) {
        Player player = playerRepository.findById(playerId).orElse(null);
        if (player != null) {
            return banRepository.findAllByPlayerUniqueId(player.getUniqueId());
        }
        return null;
    }

    public List<Ban> getBansOfAdmin(int adminId) {
        return banRepository.findAllByPanelUserId(adminId);
    }
}
