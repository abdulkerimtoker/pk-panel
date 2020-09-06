package toker.warbandscripts.panel.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import toker.warbandscripts.panel.entity.CraftingRequest;

import java.util.List;

public interface CraftingRequestRepository extends BaseRepository<CraftingRequest, Integer> {
    List<CraftingRequest> findAllByPlayerId(Integer playerId);
}
