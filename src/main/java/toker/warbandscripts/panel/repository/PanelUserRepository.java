package toker.warbandscripts.panel.repository;

import toker.warbandscripts.panel.entity.PanelUser;

public interface PanelUserRepository extends BaseRepository<PanelUser, Integer> {
    PanelUser findByUsername(String username);
    PanelUser findByClaimedIdentity(String claimedIdentity);
}
