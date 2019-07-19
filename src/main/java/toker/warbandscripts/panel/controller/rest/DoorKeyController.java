package toker.warbandscripts.panel.controller.rest;

import com.fasterxml.jackson.annotation.JsonView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import toker.warbandscripts.panel.entity.DoorKey;
import toker.warbandscripts.panel.service.DoorKeyService;

@RestController
public class DoorKeyController {

    @Autowired
    private DoorKeyService doorKeyService;

    public DoorKeyController(DoorKeyService doorKeyService) {
        this.doorKeyService = doorKeyService;
    }

    @PutMapping("/api/player/doorKey")
    @JsonView(DoorKey.View.Door.class)
    public DoorKey saveDoorKey(@RequestBody DoorKey doorKey) {
        return doorKeyService.saveDoorKey(doorKey);
    }
}
