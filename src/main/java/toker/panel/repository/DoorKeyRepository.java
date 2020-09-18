package toker.panel.repository;

import toker.panel.entity.DoorKey;

import java.util.List;

public interface DoorKeyRepository extends BaseRepository<DoorKey, Integer> {
    List<DoorKey> findAllByPlayerId(Integer playerId);
}
