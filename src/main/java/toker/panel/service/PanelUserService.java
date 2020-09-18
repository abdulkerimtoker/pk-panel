package toker.panel.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import toker.panel.authentication.JWTOpenIDAuthenticationToken;
import toker.panel.entity.PanelUser;
import toker.panel.repository.PanelUserRankRepository;
import toker.panel.repository.PanelUserRepository;

import javax.persistence.criteria.JoinType;
import java.io.IOException;
import java.sql.Timestamp;
import java.time.Instant;
import java.util.Collection;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
public class PanelUserService {

    private PanelUserRepository panelUserRepository;
    private PanelUserRankRepository panelUserRankRepository;

    @Qualifier("steam-api")
    private RestTemplate restTemplate;

    public PanelUserService(PanelUserRepository panelUserRepository,
                            PanelUserRankRepository panelUserRankRepository,
                            RestTemplate restTemplate) {
        this.panelUserRepository = panelUserRepository;
        this.panelUserRankRepository = panelUserRankRepository;
        this.restTemplate = restTemplate;
    }

    public PanelUser getOrCreateForClaimedIdentity(String claimedIdentity) {
        PanelUser panelUser = panelUserRepository.findOne(((root, query, builder) -> {
            root.fetch("authorityAssignments", JoinType.LEFT);
            return builder.equal(root.get("claimedIdentity"), claimedIdentity);
        })).orElse(null);

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
                    String username = node.get("response")
                            .get("players")
                            .elements()
                            .next()
                            .get("personaname")
                            .asText();

                    panelUser = new PanelUser();
                    panelUser.setClaimedIdentity(claimedIdentity);
                    panelUser.setUsername(username);
                    panelUser.setIsLocked(false);
                    panelUser.setRank(panelUserRankRepository.getOne(1));
                    panelUser.setCreationTime(Timestamp.from(Instant.now()));
                    panelUser = panelUserRepository.saveAndFlush(panelUser);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return panelUser;
    }

    public Collection<GrantedAuthority> getAuthoritiesForCurrentUser() {
        JWTOpenIDAuthenticationToken token =
                (JWTOpenIDAuthenticationToken) SecurityContextHolder.getContext().getAuthentication();
        return token.getAuthorities();
    }
}
