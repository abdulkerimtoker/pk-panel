package toker.warbandscripts.panel.controller.rest;

import com.fasterxml.jackson.annotation.JsonView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import toker.warbandscripts.panel.entity.Door;
import toker.warbandscripts.panel.entity.DoorKey;
import toker.warbandscripts.panel.repository.DoorRepository;
import toker.warbandscripts.panel.service.DoorService;

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
}
