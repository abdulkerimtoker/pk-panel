package toker.warbandscripts.panel.service;

import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import toker.warbandscripts.panel.bean.SelectedServerId;
import toker.warbandscripts.panel.entity.Ban;
import toker.warbandscripts.panel.entity.Player;
import toker.warbandscripts.panel.entity.Server;
import toker.warbandscripts.panel.repository.BanRepository;
import toker.warbandscripts.panel.repository.PanelUserRepository;
import toker.warbandscripts.panel.repository.PlayerRepository;
import toker.warbandscripts.panel.repository.ServerRepository;

import java.io.*;
import java.sql.Timestamp;
import java.time.Instant;
import java.util.Calendar;
import java.util.List;

@Service
public class BanService {

    private PanelConfigurationService configurationService;

    private BanRepository banRepository;
    private PlayerRepository playerRepository;
    private PanelUserRepository panelUserRepository;
    private ServerRepository serverRepository;

    public BanService(PanelConfigurationService configurationService,
                      BanRepository banRepository,
                      PlayerRepository playerRepository,
                      PanelUserRepository panelUserRepository,
                      ServerRepository serverRepository) {
        this.configurationService = configurationService;
        this.banRepository = banRepository;
        this.playerRepository = playerRepository;
        this.panelUserRepository = panelUserRepository;
        this.serverRepository = serverRepository;
    }

    @Scheduled(fixedDelay = 1000 * 30)
    public void refreshBanList() throws IOException {
        for (Server server : serverRepository.findAll()) {
            File serverFolder = new File(new File(server.getExePath()).getParent());
            File banFile = new File(serverFolder, "banlist.txt");
            FileWriter writer = new FileWriter(banFile);

            for (Ban ban : banRepository.findAllByServerId(server.getId())) {
                if (ban.isUndone())
                    continue;

                if (ban.isPermanent()) {
                    writer.write(String.valueOf(ban.getPlayerUniqueId()));
                    writer.write(System.lineSeparator());
                    continue;
                }

                Calendar calendar = Calendar.getInstance();
                calendar.setTime(ban.getTime());
                calendar.add(Calendar.MINUTE, ban.getMinutes());

                if (calendar.toInstant().isAfter(Instant.now())) {
                    writer.write(String.valueOf(ban.getPlayerUniqueId()));
                    writer.write(System.lineSeparator());
                }
            }

            writer.flush();
            writer.close();
        }
    }

    public Ban banPlayer(Ban ban) {
        String adminName = (String)SecurityContextHolder
                .getContext().getAuthentication().getPrincipal();

        ban.setPanelUser(panelUserRepository.findByUsername(adminName));
        ban.setServer(serverRepository.getOne(SelectedServerId.get()));
        ban.setTime(Timestamp.from(Instant.now()));
        ban.setUndone(false);

        return banRepository.saveAndFlush(ban);
    }

    public Ban undoBan(int banId) throws ChangeSetPersister.NotFoundException {
        Ban ban = banRepository.findById(banId)
                .orElseThrow(ChangeSetPersister.NotFoundException::new);
        ban.setUndone(true);
        return banRepository.saveAndFlush(ban);
    }

    public void undoAllBansForPlayer(int playerUniqueId) {
        banRepository.undoAllByPlayerUniqueId(playerUniqueId, SelectedServerId.get());
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
