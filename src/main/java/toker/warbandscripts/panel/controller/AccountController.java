package toker.warbandscripts.panel.controller;

import net.sf.ehcache.constructs.blocking.BlockingCacheOperationOutcomes;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import toker.warbandscripts.panel.entity.PanelUser;
import toker.warbandscripts.panel.model.AccountLayoutModel;
import toker.warbandscripts.panel.model.ChangePasswordModel;
import toker.warbandscripts.panel.repository.PanelUserRepository;

import javax.validation.Valid;

@Controller
@RequestMapping("/account")
public class AccountController {

    private PanelUserRepository panelUserRepository;

    @Autowired
    public AccountController(PanelUserRepository panelUserRepository) {
        this.panelUserRepository = panelUserRepository;
    }

    @RequestMapping("layout")
    public String layout(Model model) {
        model.addAttribute("changePasswordModel", new ChangePasswordModel());

        AccountLayoutModel layoutModel = new AccountLayoutModel();
        layoutModel.setNewPasswordIsInvalid(model.containsAttribute("newPasswordIsInvalid"));
        layoutModel.setNewPasswordNotMatches(model.containsAttribute("newPasswordNotMatches"));
        layoutModel.setNewPasswordIsSet(model.containsAttribute("newPasswordIsSet"));
        layoutModel.setCurrentPasswordIsWrong(model.containsAttribute("currentPasswordIsWrong"));
        model.addAttribute("model", layoutModel);

        return "account/layout";
    }

    @PostMapping("changepassword")
    public String changePassword(@ModelAttribute("changePasswordModel") @Valid ChangePasswordModel submit,
                                 BindingResult bindingResult,
                                 RedirectAttributes redirectAttributes) {
        boolean redirect = false;
        if (bindingResult.hasFieldErrors("newPassword")) {
            redirectAttributes.addFlashAttribute("newPasswordIsInvalid", true);
            redirect = true;
        }
        if (!submit.getNewPassword().equals(submit.getNewPasswordConfirm())) {
            redirectAttributes.addFlashAttribute("newPasswordNotMatches", true);
            redirect = true;
        }
        if (redirect) {
            return "redirect:layout";
        }

        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        PanelUser panelUser = panelUserRepository.findPanelUserByUsername(username);

        if (panelUser != null) {
            if (panelUser.getPassword().equals(submit.getCurrentPassword()) &&
                panelUserRepository.changePassword(panelUser, submit.getNewPassword())) {
                redirectAttributes.addFlashAttribute("newPasswordIsSet", true);
            } else {
                redirectAttributes.addFlashAttribute("currentPasswordIsWrong", true);
            }
        }

        return "redirect:layout";
    }
}
