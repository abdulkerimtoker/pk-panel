package toker.panel.controller.rest

import com.fasterxml.jackson.annotation.JsonView
import org.springframework.web.bind.annotation.*
import toker.panel.bean.SelectedServerId
import toker.panel.entity.AdminPermissions
import toker.panel.repository.AdminPermissionsRepository
import toker.panel.repository.ServerRepository

@RestController
class PermissionsController(
    private val permissionsRepository: AdminPermissionsRepository,
    private val serverRepository: ServerRepository
) {
    @GetMapping("/api/admin/ig-permissions")
    @JsonView(AdminPermissions.View.None::class)
    fun permissions() = permissionsRepository.findAllByServerId(SelectedServerId)

    @GetMapping("/api/admin/ig-permissions/{uniqueId}")
    @JsonView(AdminPermissions.View.None::class)
    fun permissions(@PathVariable uniqueId: Int) = permissionsRepository.findByServerIdAndUniqueId(SelectedServerId, uniqueId)

    @DeleteMapping("/api/admin/ig-permissions/{uniqueId}")
    fun revokePermissions(@PathVariable uniqueId: Int) = permissionsRepository.deleteByServerIdAndUniqueId(SelectedServerId, uniqueId)

    @PostMapping("/api/admin/ig-permissions/{uniqueId}")
    @JsonView(AdminPermissions.View.None::class)
    fun permissions(@PathVariable uniqueId: Int, @RequestBody permissions: AdminPermissions): AdminPermissions {
        permissions.server = serverRepository.getOne(SelectedServerId)
        permissions.uniqueId = uniqueId
        return permissionsRepository.saveAndFlush(permissions)
    }
}