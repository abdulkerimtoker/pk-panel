package toker.warbandscripts.panel.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import toker.warbandscripts.panel.entity.AdminPermissions;
import toker.warbandscripts.panel.model.PermissionsListModel;
import toker.warbandscripts.panel.repository.IGPermissionsRepository;

import java.util.List;

@Controller
@RequestMapping("/permissions")
public class IGPermissionsController {

    private IGPermissionsRepository permissionsRepository;

    @Autowired
    public IGPermissionsController(IGPermissionsRepository permissionsRepository) {
        this.permissionsRepository = permissionsRepository;
    }

    @RequestMapping("list")
    @Secured("ROLE_PERMISSION_MANAGER")
    public String list(@ModelAttribute("model") PermissionsListModel model) {
        model.setPermissions(permissionsRepository.getPermissions());
        return "permissions/list";
    }

    @PostMapping("update")
    @Secured("ROLE_PERMISSION_MANAGER")
    public String update(@ModelAttribute("model") PermissionsListModel model) {
        permissionsRepository.updatePermissions(model.getPermissions());
        return "redirect:list";
    }

    @PostMapping("add")
    @Secured("ROLE_PERMISSION_MANAGER")
    public String add(@RequestParam int uniqueId) {
        permissionsRepository.addPermissions(uniqueId);
        return "redirect:list";
    }

    @PostMapping("delete")
    @Secured("ROLE_PERMISSION_MANAGER")
    public String delete(@RequestParam int uniqueId) {
        permissionsRepository.deletePermissions(uniqueId);
        return "redirect:list";
    }
}
