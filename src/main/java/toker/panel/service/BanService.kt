package toker.panel.service

import org.springframework.context.annotation.Profile
import org.springframework.data.crossstore.ChangeSetPersister
import org.springframework.scheduling.annotation.Scheduled
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.stereotype.Service
import toker.panel.bean.SelectedServerId
import toker.panel.entity.Ban
import toker.panel.repository.BanRepository
import toker.panel.repository.PanelUserRepository
import toker.panel.repository.PlayerRepository
import toker.panel.repository.ServerRepository
import java.io.File
import java.io.FileWriter
import java.io.IOException
import java.sql.Timestamp
import java.time.Instant
import java.util.*

@Service
class BanService(private val banRepository: BanRepository,
                 private val playerRepository: PlayerRepository,
                 private val panelUserRepository: PanelUserRepository,
                 private val serverRepository: ServerRepository) {

    @Scheduled(fixedDelay = 1000 * 30)
    @Throws(IOException::class)
    fun refreshBanList() {
        for (server in serverRepository.findAll()) {
            val serverFolder = File(File(server.exePath!!).parent)
            val banFile = File(serverFolder, "banlist.txt")
            val writer = FileWriter(banFile)
            for (ban in banRepository.findAllByServerId(server.id!!)) {
                if (ban.isUndone!!) continue
                if (ban.isPermanent!!) {
                    writer.write(ban.playerUniqueId.toString())
                    writer.write(System.lineSeparator())
                    continue
                }
                val calendar = Calendar.getInstance()
                calendar.time = ban.time
                calendar.add(Calendar.MINUTE, ban.minutes!!)
                if (calendar.toInstant().isAfter(Instant.now())) {
                    writer.write(ban.playerUniqueId.toString())
                    writer.write(System.lineSeparator())
                }
            }
            writer.flush()
            writer.close()
        }
    }

    fun banPlayer(ban: Ban): Ban {
        val adminName = SecurityContextHolder
                .getContext().authentication.principal as String
        ban.panelUser = panelUserRepository.findByUsername(adminName)
        ban.server = serverRepository.getOne(SelectedServerId)
        ban.time = Timestamp.from(Instant.now())
        ban.isUndone = false
        return banRepository.saveAndFlush(ban)
    }

    @Throws(ChangeSetPersister.NotFoundException::class)
    fun undoBan(banId: Int): Ban {
        val ban = banRepository.findById(banId)
                .orElseThrow { ChangeSetPersister.NotFoundException() }
        ban.isUndone = true
        return banRepository.saveAndFlush(ban)
    }

    fun undoAllBansForPlayer(playerUniqueId: Int) {
        banRepository.undoAllByPlayerUniqueId(playerUniqueId, SelectedServerId)
    }

    fun getBansOfPlayer(playerId: Int): List<Ban>? {
        val player = playerRepository.findById(playerId).orElse(null)
        return if (player != null) {
            banRepository.findAllByPlayerUniqueId(player.uniqueId!!)
        } else null
    }

    fun getBansOfAdmin(adminId: Int): List<Ban> {
        return banRepository.findAllByPanelUserId(adminId)
    }
}