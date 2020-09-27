package toker.panel.repository;

import toker.panel.entity.CraftingStation;
import toker.panel.entity.pk.CraftingStationPK;

import java.util.List;

public interface CraftingStationRepository
        extends BaseRepository<CraftingStation, CraftingStationPK> {
    List<CraftingStation> findAllByServerId(Integer serverId);
}
