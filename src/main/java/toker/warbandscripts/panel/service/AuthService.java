package toker.warbandscripts.panel.service;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import toker.warbandscripts.panel.authentication.JWTOpenIDAuthenticationToken;
import toker.warbandscripts.panel.entity.Player;
import toker.warbandscripts.panel.entity.Server;
import toker.warbandscripts.panel.repository.PlayerRepository;
import toker.warbandscripts.panel.repository.ServerRepository;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service("authService")
public class AuthService {

    private ServerRepository serverRepository;
    private PlayerRepository playerRepository;

    private Map<Class<?>, Method> typeToGetterMap = new HashMap<>();

    public AuthService(ServerRepository serverRepository,
                       PlayerRepository playerRepository) {
        this.serverRepository = serverRepository;
        this.playerRepository = playerRepository;
    }

    public boolean canModifyPlayer(int playerId) {
        Player current = playerRepository.findById(playerId).orElse(null);

        if (current == null) {
            return true;
        }

        JWTOpenIDAuthenticationToken token =
                (JWTOpenIDAuthenticationToken) SecurityContextHolder.getContext().getAuthentication();

        return token.getAuthorities()
                .stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.toList())
                .contains(String.format("ROLE_%d_PLAYER_MANAGER", current.getFaction().getServer().getId()));
    }

    public List<Server> getServersForAdmin() {
        JWTOpenIDAuthenticationToken token =
                (JWTOpenIDAuthenticationToken) SecurityContextHolder.getContext().getAuthentication();
        List<String> authorities = token.getAuthorities()
                .stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.toList());

        return serverRepository.findAll()
                .stream()
                .filter(server -> authorities.contains(String.format("ROLE_%d_USER", server.getId())))
                .collect(Collectors.toList());
    }
}
