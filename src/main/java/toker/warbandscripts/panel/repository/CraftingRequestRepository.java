package toker.warbandscripts.panel.repository;

import toker.warbandscripts.panel.entity.CraftingRequest;

import java.util.List;

public interface CraftingRequestRepository extends BaseRepository<CraftingRequest, Integer> {
    List<CraftingRequest> findAllByPlayerId(Integer playerId);
}
