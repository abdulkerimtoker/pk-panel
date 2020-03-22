package toker.warbandscripts.panel.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.web.authentication.rememberme.PersistentTokenRepository;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import toker.warbandscripts.panel.entity.PanelUser;
import toker.warbandscripts.panel.entity.PanelUserRank;
import toker.warbandscripts.panel.repository.PanelUserRepository;

import java.io.IOException;
import java.sql.Timestamp;
import java.time.Instant;
import java.util.Map;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
public class PanelUserService {

    private PanelUserRepository panelUserRepository;

    @Qualifier("steam-api")
    private RestTemplate restTemplate;

    public PanelUserService(PanelUserRepository panelUserRepository, RestTemplate restTemplate) {
        this.panelUserRepository = panelUserRepository;
        this.restTemplate = restTemplate;
    }

    public PanelUser getOrCreateForClaimedIdentity(String claimedIdentity) {
        PanelUser panelUser = panelUserRepository.findByClaimedIdentity(claimedIdentity);
        if (panelUser == null) {
            Pattern pattern = Pattern.compile("https://steamcommunity\\.com/openid/id/(?<steamid>\\d+)");
            Matcher matcher = pattern.matcher(claimedIdentity);

            if (matcher.find()) {
                String steamId = matcher.group("steamid");
                String sea = restTemplate.getForObject(
                        String.format("http://api.steampowered.com/ISteamUser/GetPlayerSummaries/v0002/?steamids=%s", steamId), String.class);
                ObjectMapper mapper = new ObjectMapper();
                try {
                    JsonNode node = mapper.readTree(sea);
                    String username = node.get("response").get("players").elements().next().get("personaname").asText();
                    panelUser = new PanelUser();
                    panelUser.setClaimedIdentity(claimedIdentity);
                    panelUser.setUsername(username);
                    panelUser.setIsLocked(false);
                    PanelUserRank rank = new PanelUserRank();
                    rank.setId(1);
                    panelUser.setRank(rank);
                    panelUser.setCreationTime(Timestamp.from(Instant.now()));
                    panelUser = panelUserRepository.save(panelUser);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return panelUser;
    }

    public boolean isUserToBeLoggedOut(PanelUser panelUser) {
        return false;
    }
}
