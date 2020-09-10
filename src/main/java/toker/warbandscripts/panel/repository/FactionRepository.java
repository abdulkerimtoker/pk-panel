package toker.warbandscripts.panel.repository;

import toker.warbandscripts.panel.entity.Faction;
import toker.warbandscripts.panel.entity.pk.FactionPK;

import java.util.List;

public interface FactionRepository extends BaseRepository<Faction, FactionPK> {

    List<Faction> findAllByServerId(Integer serverId);
}
