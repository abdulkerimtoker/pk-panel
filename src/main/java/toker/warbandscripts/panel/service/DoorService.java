package toker.warbandscripts.panel.service;

import org.springframework.stereotype.Service;
import toker.warbandscripts.panel.entity.Door;
import toker.warbandscripts.panel.entity.DoorKey;
import toker.warbandscripts.panel.repository.DoorKeyRepository;
import toker.warbandscripts.panel.repository.DoorRepository;
import toker.warbandscripts.panel.repository.PlayerRepository;

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

    public Optional<Door> getDoor(int doorId) {
        return doorRepository.findById(doorId);
    }

    public List<Door> getAllDoors() {
        return doorRepository.findAll();
    }

    public DoorKey assignDoorKey(DoorKey doorKey) {
        return this.doorKeyRepository.saveAndFlush(doorKey);
    }

    public void revokeDoorKey(int doorKeyId) {
        doorKeyRepository.deleteById(doorKeyId);
    }
}
