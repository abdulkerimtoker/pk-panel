package toker.panel.repository

import org.springframework.data.jpa.domain.Specification
import org.springframework.data.jpa.repository.Lock
import org.springframework.data.jpa.repository.Modifying
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param
import toker.panel.entity.Player
import java.util.*
import javax.persistence.LockModeType
import javax.transaction.Transactional

interface PlayerRepository : BaseRepository<Player, Int> {
    @Query("SELECT p FROM Player p " +
            "WHERE (" +
            "LOWER(p.name) LIKE CONCAT('%',LOWER(?1),'%') " +
            "OR CAST(p.uniqueId as text) LIKE CONCAT('%',?1,'%') " +
            "OR CAST(p.id as text) LIKE CONCAT('%',?1,'%')" +
            ")" +
            "AND p.faction.server = ?2")
    fun likeSearch(likeString: String, server: Int): List<Player>

    @Query("""
        UPDATE Player p
        SET p.woundTime = current_timestamp, p.woundDuration = :duration
        WHERE p.id = :id
    """)
    @Modifying
    @Transactional
    fun wound(@Param("id") id: Int, @Param("duration") duration: Int)
}