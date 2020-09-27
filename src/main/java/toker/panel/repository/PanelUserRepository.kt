package toker.panel.repository;

import toker.panel.entity.PanelUser;

public interface PanelUserRepository extends BaseRepository<PanelUser, Integer> {
    PanelUser findByUsername(String username);
    PanelUser findByClaimedIdentity(String claimedIdentity);
}
