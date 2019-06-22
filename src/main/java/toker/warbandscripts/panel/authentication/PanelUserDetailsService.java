package toker.warbandscripts.panel.authentication;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import toker.warbandscripts.panel.entity.PanelUser;
import toker.warbandscripts.panel.repository.PanelUserRepository;


@Service
public class PanelUserDetailsService implements UserDetailsService {

    private PanelUserRepository panelUserRepository;

    @Autowired(required = true)
    public PanelUserDetailsService(PanelUserRepository panelUserRepository) {
        this.panelUserRepository = panelUserRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        PanelUser panelUser = panelUserRepository.findPanelUserByUsername(username);
        if (panelUser == null)
            throw new UsernameNotFoundException("No such user");
        return new PanelUserDetails(panelUser);
    }
}
