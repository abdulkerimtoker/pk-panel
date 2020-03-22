package toker.warbandscripts.panel.repository;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import toker.warbandscripts.panel.entity.PanelUser;

public interface PanelUserRepository extends JpaRepository<PanelUser, Integer> {
    PanelUser findByUsername(String username);
    PanelUser findByClaimedIdentity(String claimedIdentity);
}
