package toker.panel.repository

import org.springframework.data.jpa.repository.Modifying
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param
import toker.panel.entity.Door
import toker.panel.entity.pk.DoorPK
import javax.transaction.Transactional

interface DoorRepository : BaseRepository<Door, DoorPK> {
    @Modifying
    @Transactional
    @Query("UPDATE Door d SET d.locked = :locked " +
            "WHERE d.index = :index AND d.server.id = :serverId")
    fun changeLockState(@Param("index") index: Int,
                        @Param("serverId") serverId: Int,
                        @Param("locked") locked: Boolean): Int

    fun findAllByServerId(serverId: Int): List<Door>
}