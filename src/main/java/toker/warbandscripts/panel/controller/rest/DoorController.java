package toker.warbandscripts.panel.controller.rest;

import com.fasterxml.jackson.annotation.JsonView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import toker.warbandscripts.panel.entity.Door;
import toker.warbandscripts.panel.entity.DoorKey;
import toker.warbandscripts.panel.service.DoorService;

import java.util.List;

@RestController
public class DoorController {

    @Autowired
    private DoorService doorService;

    public DoorController(DoorService doorService) {
        this.doorService = doorService;
    }

    @GetMapping("/api/door/{doorId}")
    @JsonView(DoorKey.View.Player.class)
    public Door door(@PathVariable int doorId) {
        return doorService.getDoor(doorId).orElse(null);
    }

    @GetMapping("/api/door")
    @JsonView(Door.View.class)
    public List<Door> door() {
        return doorService.getAllDoors();
    }

    @PutMapping("/api/player/doorKey")
    @JsonView(DoorKey.View.Door.class)
    public DoorKey saveDoorKey(@RequestBody DoorKey doorKey) {
        return doorService.saveDoorKey(doorKey);
    }
}
