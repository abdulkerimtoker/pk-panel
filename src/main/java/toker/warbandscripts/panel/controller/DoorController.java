package toker.warbandscripts.panel.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import toker.warbandscripts.panel.entity.Door;
import toker.warbandscripts.panel.model.DoorListModel;
import toker.warbandscripts.panel.model.DoorManageModel;
import toker.warbandscripts.panel.repository.DoorRepository;

@Controller
@RequestMapping("/door")
public class DoorController {

    private DoorRepository doorRepository;

    @Autowired(required = true)
    public DoorController(DoorRepository doorRepository) {
        this.doorRepository = doorRepository;
    }

    @RequestMapping("list")
    public String list(Model model) {
        DoorListModel listModel = new DoorListModel();
        listModel.setDoors(doorRepository.getDoors());
        model.addAttribute("model", listModel);
        return "door/list";
    }

    @GetMapping("manage/{doorId}")
    public String manage(@ModelAttribute("model") DoorManageModel manageModel, @PathVariable int doorId) {
        manageModel.setDoor(doorRepository.getDoor(doorId));
        return "door/manage";
    }

    @Secured("ROLE_DOOR_MANAGER")
    @PostMapping("manage")
    public String processManage(@ModelAttribute("model") DoorManageModel form, BindingResult bindingResult) {
        if (!bindingResult.hasErrors()) {
            doorRepository.updateDoor(form.getDoor());
        }
        return "redirect:manage/" + form.getDoor().getId();
    }

    @Secured("ROLE_DOOR_MANAGER")
    @PostMapping("removedoorkey")
    public String removeDoorKey(int doorKeyId, int doorId) {
        doorRepository.removeDoorKey(doorKeyId);
        return "redirect:manage/" + doorId;
    }
}
