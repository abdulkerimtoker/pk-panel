package toker.panel.controller.rest

import com.fasterxml.jackson.annotation.JsonView
import org.springframework.data.crossstore.ChangeSetPersister
import org.springframework.web.bind.annotation.*
import org.springframework.web.multipart.MultipartFile
import toker.panel.bean.SelectedServerId
import toker.panel.entity.Server
import toker.panel.entity.Server.View.StartupCommands
import toker.panel.service.AuthService
import toker.panel.service.ServerService
import java.io.File
import java.io.IOException

@RestController
class ServerController(private val authService: AuthService,
                       private val serverService: ServerService) {
    @GetMapping("/api/servers")
    @JsonView(Server.View.None::class)
    fun servers(): List<Server> {
        return authService.serversForAdmin
    }

    @GetMapping("/api/servers/{serverId}")
    @JsonView(StartupCommands::class)
    @Throws(ChangeSetPersister.NotFoundException::class)
    fun server(@PathVariable serverId: Int): Server {
        return serverService.getServer(serverId, "startupCommands")
    }

    @PostMapping("/api/uploadMap")
    @Throws(ChangeSetPersister.NotFoundException::class, IOException::class)
    fun uploadMap(@RequestParam map: MultipartFile) {
        val server = serverService.getServer(SelectedServerId)
        map.transferTo(File(serverService.getMapDir(server), map.originalFilename!!))
    }
}