package toker.warbandscripts.panel.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import toker.warbandscripts.panel.entity.Door;
import toker.warbandscripts.panel.entity.DoorKey;
import toker.warbandscripts.panel.repository.DoorKeyRepository;
import toker.warbandscripts.panel.repository.DoorRepository;

import java.util.List;
import java.util.Optional;

@Service
public class DoorService {

    private DoorRepository doorRepository;
    private DoorKeyRepository doorKeyRepository;

    public DoorService(DoorRepository doorRepository, DoorKeyRepository doorKeyRepository) {
        this.doorRepository = doorRepository;
        this.doorKeyRepository = doorKeyRepository;
    }

    public Optional<Door> getDoor(int doorId) {
        return doorRepository.findById(doorId);
    }

    public List<Door> getAllDoors() {
        return doorRepository.findAll();
    }

    public DoorKey saveDoorKey(DoorKey doorKey) {
        return doorKeyRepository.saveAndFlush(doorKey);
    }
}
