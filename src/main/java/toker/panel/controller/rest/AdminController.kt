package toker.panel.controller.rest

import com.fasterxml.jackson.annotation.JsonView
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController
import toker.panel.entity.PanelUser
import toker.panel.entity.PanelUser_
import toker.panel.repository.PanelUserRepository

@RestController
class AdminController(
        private val panelUserRepo: PanelUserRepository
) {

    @GetMapping("/api/admins")
    @JsonView(PanelUser.View.AuthorityAssignments::class)
    fun admins(): MutableList<PanelUser> {
        return panelUserRepo.findAll()
    }
}