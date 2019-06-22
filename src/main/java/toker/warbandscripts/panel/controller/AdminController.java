package toker.warbandscripts.panel.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import toker.warbandscripts.panel.entity.PanelUser;
import toker.warbandscripts.panel.model.AdminCreateModel;
import toker.warbandscripts.panel.model.AdminListModel;
import toker.warbandscripts.panel.model.AdminManageModel;
import toker.warbandscripts.panel.model.AdminManageSubmitModel;
import toker.warbandscripts.panel.repository.PanelUserRepository;

import javax.validation.Valid;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.util.LinkedList;
import java.util.List;

@Controller
@RequestMapping("/admin")
public class AdminController {

    private PanelUserRepository panelUserRepository;

    @Autowired
    public AdminController(PanelUserRepository panelUserRepository) {
        this.panelUserRepository = panelUserRepository;
    }

    @RequestMapping("list")
    public String list(@ModelAttribute("model") AdminListModel model, @ModelAttribute("createModel") AdminCreateModel createModel) {
        model.setAdmins(panelUserRepository.getPanelUsers());
        return "admin/list";
    }

    @PostMapping("create")
    public String create(@ModelAttribute("createModel") AdminCreateModel form, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "admin/list";
        }

        if (panelUserRepository.createPanelUser(form.getUsername(), form.getPassword())) {
            PanelUser panelUser = panelUserRepository.findPanelUserByUsername(form.getUsername());
            return "redirect:manage/" + panelUser.getId();
        } else {
            return "admin/list";
        }
    }

    @GetMapping("manage/{adminId}")
    public String manage(Model model, @PathVariable int adminId) {
        PanelUser admin = panelUserRepository.findPanelUserById(adminId);
        AdminManageModel manageModel = new AdminManageModel();
        manageModel.setAdmin(admin);
        manageModel.setRanks(panelUserRepository.getRanks());
        manageModel.setAuthorities(panelUserRepository.getAuthorities());
        model.addAttribute("model", manageModel);
        return "admin/manage";
    }

    @PostMapping("manage")
    public String processManage(@ModelAttribute("model") @Valid AdminManageSubmitModel form,
                                BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "admin/manage/" + form.getAdminId();
        }
        List<Integer> authorityIds = new LinkedList<>();
        if (form.getAuthorities() != null) {
            for (String authorityIdText : form.getAuthorities().split(",")) {
                authorityIds.add(Integer.parseInt(authorityIdText));
            }
        }
        panelUserRepository.updatePanelUser(form.getAdminId(), form.getUsername(), form.getRankId(), authorityIds);
        return "redirect:manage/" + form.getAdminId();
    }
}
