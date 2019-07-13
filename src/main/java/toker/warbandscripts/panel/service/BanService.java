package toker.warbandscripts.panel.service;

import org.apache.commons.net.ftp.FTPClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import toker.warbandscripts.panel.entity.Ban;

import java.io.*;
import java.util.LinkedHashSet;

@Service
public class BanService {

    private PanelConfigurationService configurationService;

    @Autowired
    public BanService(PanelConfigurationService configurationService) {
        this.configurationService = configurationService;
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
}
