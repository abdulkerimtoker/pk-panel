package toker.warbandscripts.panel.repository;

import toker.warbandscripts.panel.entity.CraftingStation;
import toker.warbandscripts.panel.entity.pk.CraftingStationPK;

import java.util.List;

public interface CraftingStationRepository
        extends BaseRepository<CraftingStation, CraftingStationPK> {
    List<CraftingStation> findAllByServerId(Integer serverId);
}
