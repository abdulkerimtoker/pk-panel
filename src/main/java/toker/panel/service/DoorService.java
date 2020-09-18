package toker.panel.service;

import org.springframework.stereotype.Service;
import toker.panel.bean.SelectedServerId;
import toker.panel.entity.Door;
import toker.panel.entity.DoorKey;
import toker.panel.entity.pk.DoorPK;
import toker.panel.repository.DoorKeyRepository;
import toker.panel.repository.DoorRepository;
import toker.panel.repository.PlayerRepository;

import java.util.List;
import java.util.Optional;

@Service
public class DoorService {

    private DoorRepository doorRepository;
    private DoorKeyRepository doorKeyRepository;
    private PlayerRepository playerRepository;

    public DoorService(DoorRepository doorRepository,
                       DoorKeyRepository doorKeyRepository,
                       PlayerRepository playerRepository) {
        this.doorRepository = doorRepository;
        this.doorKeyRepository = doorKeyRepository;
        this.playerRepository = playerRepository;
    }

    public Optional<Door> getDoor(DoorPK doorId) {
        return doorRepository.findById(doorId);
    }

    public List<Door> getAllDoors() {
        return doorRepository.findAllByServerId(SelectedServerId.get());
    }

    public DoorKey assignDoorKey(DoorKey doorKey) {
        return this.doorKeyRepository.saveAndFlush(doorKey);
    }

    public void revokeDoorKey(int doorKeyId) {
        doorKeyRepository.deleteById(doorKeyId);
    }

    public boolean changeLockState(int index, int serverId, boolean locked) {
        return doorRepository.changeLockState(index, serverId, locked) > 0;
    }
}
