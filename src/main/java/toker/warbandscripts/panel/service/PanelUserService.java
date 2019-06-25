package toker.warbandscripts.panel.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.web.authentication.rememberme.PersistentTokenRepository;
import org.springframework.stereotype.Service;
import toker.warbandscripts.panel.entity.PanelUser;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

@Service
public class PanelUserService {

    private PersistentTokenRepository rememberMeTokenRepository;

    private BlockingQueue<PanelUser> usersToLogOut = new LinkedBlockingQueue<>();

    @Autowired
    public PanelUserService(PersistentTokenRepository rememberMeTokenRepository) {
        this.rememberMeTokenRepository = rememberMeTokenRepository;
    }

    public void logUserOut(PanelUser panelUser) {
        if (!usersToLogOut.contains(panelUser)) {
            usersToLogOut.offer(panelUser);
        }
        rememberMeTokenRepository.removeUserTokens(panelUser.getUsername());
    }

    public boolean isUserToBeLoggedOut(PanelUser panelUser) {
        boolean isToBeLoggedOut = usersToLogOut.remove(panelUser);
        if (isToBeLoggedOut) {
            rememberMeTokenRepository.removeUserTokens(panelUser.getUsername());
        }
        return isToBeLoggedOut;
    }
}
