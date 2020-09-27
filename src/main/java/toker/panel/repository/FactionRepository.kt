package toker.panel.repository;

import toker.panel.entity.Faction;
import toker.panel.entity.pk.FactionPK;

import java.util.List;

public interface FactionRepository extends BaseRepository<Faction, FactionPK> {

    List<Faction> findAllByServerId(Integer serverId);
}
