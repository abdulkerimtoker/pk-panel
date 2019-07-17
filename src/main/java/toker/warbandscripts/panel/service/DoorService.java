package toker.warbandscripts.panel.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import toker.warbandscripts.panel.entity.Door;
import toker.warbandscripts.panel.repository.DoorRepository;

import java.util.List;
import java.util.Optional;

@Service
public class DoorService {

    @Autowired
    private DoorRepository doorRepository;

    public DoorService(DoorRepository doorRepository) {
        this.doorRepository = doorRepository;
    }

    public Optional<Door> getDoor(int doorId) {
        return doorRepository.findById(doorId);
    }

    public List<Door> getAllDoors() {
        return doorRepository.findAll();
    }
}
