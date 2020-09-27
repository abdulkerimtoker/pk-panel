package toker.panel.repository

import org.springframework.data.jpa.repository.Lock
import org.springframework.data.jpa.repository.Query
import toker.panel.entity.Player
import javax.persistence.LockModeType

interface PlayerRepository : BaseRepository<Player, Int> {
    @Query("SELECT p FROM Player p " +
            "WHERE (" +
            "LOWER(p.name) LIKE CONCAT('%',LOWER(?1),'%') " +
            "OR CAST(p.uniqueId as text) LIKE CONCAT('%',?1,'%') " +
            "OR CAST(p.id as text) LIKE CONCAT('%',?1,'%')" +
            ")" +
            "AND p.faction.server = ?2")
    fun likeSearch(likeString: String, server: Int): List<Player>
    @Lock(LockModeType.OPTIMISTIC_FORCE_INCREMENT)
    override fun <S : Player> saveAndFlush(s: S): S
}