package toker.panel.repository;

import toker.panel.entity.CraftingRequest;

import java.util.List;

public interface CraftingRequestRepository extends BaseRepository<CraftingRequest, Integer> {
    List<CraftingRequest> findAllByPlayerId(Integer playerId);
}
