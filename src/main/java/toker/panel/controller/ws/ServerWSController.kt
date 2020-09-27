package toker.panel.controller.ws

import org.springframework.data.crossstore.ChangeSetPersister
import org.springframework.messaging.handler.annotation.MessageMapping
import org.springframework.stereotype.Controller
import toker.panel.bean.SelectedServerId
import toker.panel.service.DedicatedServerService
import toker.panel.service.ServerService
import java.io.IOException

@Controller
class ServerWSController(private val serverService: ServerService,
                         private val dedicatedServerService: DedicatedServerService) {
    @MessageMapping("/start")
    @Throws(ChangeSetPersister.NotFoundException::class, IOException::class)
    fun start() {
        val server = serverService.getServer(SelectedServerId, "startupCommands")
        dedicatedServerService.startServer(server)
    }

    @MessageMapping("/shutdown")
    @Throws(ChangeSetPersister.NotFoundException::class)
    fun shutdown() {
        dedicatedServerService.shutdownServer(
                serverService.getServer(SelectedServerId))
    }
}