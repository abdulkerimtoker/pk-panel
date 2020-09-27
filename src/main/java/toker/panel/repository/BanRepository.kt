package toker.panel.repository

import org.springframework.data.jpa.repository.Modifying
import org.springframework.data.jpa.repository.Query
import org.springframework.transaction.annotation.Transactional
import toker.panel.entity.Ban

interface BanRepository : BaseRepository<Ban, Int> {
    fun findAllByPlayerUniqueId(playerUniqueId: Int): List<Ban>
    fun findAllByPanelUserId(panelUserId: Int): List<Ban>
    fun findAllByServerId(serverId: Int): List<Ban>

    @Modifying
    @Transactional
    @Query("UPDATE Ban SET isUndone = true WHERE playerUniqueId = :playerUniqueId " +
            "AND server.id = :serverId")
    fun undoAllByPlayerUniqueId(playerUniqueId: Int, serverId: Int)
}