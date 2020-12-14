package toker.panel.controller.rest

import com.fasterxml.jackson.annotation.JsonView
import org.springframework.data.crossstore.ChangeSetPersister
import org.springframework.web.bind.annotation.*
import org.springframework.web.multipart.MultipartFile
import toker.panel.bean.SelectedServerId
import toker.panel.entity.Faction
import toker.panel.entity.Server
import toker.panel.entity.Server.View.StartupCommands
import toker.panel.entity.ServerStartupCommand
import toker.panel.entity.pk.ServerStartupCommandPK
import toker.panel.repository.FactionRepository
import toker.panel.repository.ServerRepository
import toker.panel.repository.ServerStartupCommandRepository
import toker.panel.service.AuthService
import toker.panel.service.ServerService
import java.io.File
import java.io.IOException

@RestController
class ServerController(private val authService: AuthService,
                       private val serverService: ServerService,
                       private val serverStartupCommandRepository: ServerStartupCommandRepository,
                       private val serverRepository: ServerRepository,
                       private val factionRepository: FactionRepository) {

    @GetMapping("/api/servers")
    @JsonView(Server.View.None::class)
    fun servers(): List<Server> = authService.serversForAdmin

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

    @PostMapping("/api/uploadModuleFile")
    @Throws(ChangeSetPersister.NotFoundException::class, IOException::class)
    fun uploadModuleFile(@RequestParam file: MultipartFile) {
        val server = serverService.getServer(SelectedServerId)
        file.transferTo(File(serverService.getModuleDir(server), file.originalFilename!!))
    }

    @GetMapping("/api/servers/{serverId}/startupCommands")
    @JsonView(ServerStartupCommand.View.None::class)
    fun startupCommands(@PathVariable serverId: Int) = serverService.getStartupCommands(serverId)

    @PostMapping("/api/servers/startupCommands")
    fun setStartupCommand(@RequestBody command: ServerStartupCommand) {
        command.server = serverRepository.getOne(SelectedServerId)
        serverStartupCommandRepository.saveAndFlush(command)
    }

    @DeleteMapping("/api/servers/startupCommands/{command}")
    fun removeStartupCommand(@PathVariable command: String) {
        serverStartupCommandRepository.deleteById(ServerStartupCommandPK(server = SelectedServerId, command = command))
    }

    @GetMapping("/api/servers/factions")
    @JsonView(Faction.View.None::class)
    fun factions() = serverService.getFactions(SelectedServerId)

    @PutMapping("/api/servers/factions")
    fun saveFaction(@RequestBody faction: Faction) {
        faction.server = serverRepository.getOne(SelectedServerId)
        factionRepository.saveAndFlush(faction)
    }
}