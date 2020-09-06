package toker.warbandscripts.panel.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import toker.warbandscripts.panel.entity.DoorKey;

import java.util.List;

public interface DoorKeyRepository extends BaseRepository<DoorKey, Integer> {
    List<DoorKey> findAllByPlayerId(Integer playerId);
}
