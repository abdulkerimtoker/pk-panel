package toker.warbandscripts.panel.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import toker.warbandscripts.panel.entity.Door;
import toker.warbandscripts.panel.entity.DoorKey;
import toker.warbandscripts.panel.repository.DoorKeyRepository;

@Service
public class DoorKeyService {

    @Autowired
    private DoorKeyRepository doorKeyRepository;

    public DoorKeyService(DoorKeyRepository doorKeyRepository) {
        this.doorKeyRepository = doorKeyRepository;
    }

    public DoorKey saveDoorKey(DoorKey doorKey) {
        return doorKeyRepository.saveAndFlush(doorKey);
    }
}
