package toker.panel.service

import org.springframework.messaging.simp.SimpMessagingTemplate
import org.springframework.scheduling.annotation.Scheduled
import org.springframework.stereotype.Service
import toker.panel.entity.Server
import toker.panel.repository.ServerRepository
import java.io.File
import java.io.FileWriter
import java.io.IOException
import java.net.Socket
import java.util.*
import javax.annotation.PreDestroy

@Service
class DedicatedServerService(private val serverRepository: ServerRepository,
                             private val messagingTemplate: SimpMessagingTemplate) {
    private val processMap: MutableMap<Int?, Process?> = HashMap()
    @Throws(IOException::class)
    fun startServer(server: Server) {
        if (isServerUp(server)) {
            shutdownServer(server)
        }
        val wseFile = File(server.wsePath)
        val exeFile = File(server.exePath)
        val serverFolder = File(exeFile.parent)
        val configFile = File(serverFolder, "config.txt")
        val writer = FileWriter(configFile)
        for ((_, command, value) in server.startupCommands!!) {
            writer.write(String.format("%s %s", command, value))
            writer.write(System.lineSeparator())
        }
        writer.write("set_server_ban_list_file banlist.txt")
        writer.write(System.lineSeparator())
        writer.write("set_server_log_folder logs")
        writer.write(System.lineSeparator())
        writer.write(String.format("set_port %d", server.port))
        writer.write(System.lineSeparator())
        writer.write("set_upload_limit 100000000")
        writer.write(System.lineSeparator())
        writer.write("start")
        writer.write(System.lineSeparator())
        writer.flush()
        writer.close()
        val cmds: MutableList<String?> = LinkedList()
        if (server.useScreen != null && server.useScreen!!) {
            cmds.addAll(Arrays.asList(*String.format("screen -d -m -S %s", server.name).split(" ").toTypedArray()))
        }
        if (server.frontCmd != null) {
            cmds.add(server.frontCmd)
        }
        cmds.add(server.wsePath)
        cmds.add("-p")
        cmds.add(server.exePath)
        cmds.add("-r")
        cmds.add("config.txt")
        cmds.add("-m")
        cmds.add(server.moduleName)
        val pb = ProcessBuilder(cmds)
        processMap[server.id] = pb.start()
        messagingTemplate.convertAndSend(String.format("/channel/%d/state", server.id),
                "starting_up")
    }

    fun shutdownServer(server: Server) {
        val process = processMap.getOrDefault(server.id, null)
        if (process != null) {
            if (process.isAlive) {
                process.descendants().forEach { obj: ProcessHandle -> obj.destroy() }
                process.destroy()
            }
        }
        val pb = ProcessBuilder("screen", "-S", server.name, "-X", "quit")
        try {
            pb.start()
        } catch (e: IOException) {
            e.printStackTrace()
        }
    }

    fun isServerUp(server: Server): Boolean {
        if (server.useScreen != null && server.useScreen!!) {
            var up = false
            try {
                val socket = Socket("127.0.0.1", server.port!!)
                val outputStream = socket.getOutputStream()
                outputStream.write("test".toByteArray())
                outputStream.flush()
                socket.close()
                up = true
            } catch (ignored: IOException) {
            }
            return up
        }
        val process = processMap.getOrDefault(server.id, null)
        return process != null && process.isAlive
    }

    @PreDestroy
    private fun shutdownAllServers() {
        for (server in serverRepository.findAll()) {
            if (isServerUp(server)) {
                shutdownServer(server)
            }
        }
    }

    @Scheduled(fixedDelay = 5000)
    private fun broadcastServerStates() {
        for (server in serverRepository.findAll()) {
            val process = processMap.getOrDefault(server.id, null)
            val up = if (server.useScreen != null && server.useScreen!!) isServerUp(server) else process != null && process.isAlive
            messagingTemplate.convertAndSend(String.format("/channel/%d/state", server.id),
                    if (up) "online" else "offline")
        }
    }
}