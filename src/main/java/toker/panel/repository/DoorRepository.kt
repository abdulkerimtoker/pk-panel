package toker.panel.repository;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import toker.panel.entity.Door;
import toker.panel.entity.pk.DoorPK;

import javax.transaction.Transactional;
import java.util.List;

public interface DoorRepository extends BaseRepository<Door, DoorPK> {

    @Modifying
    @Transactional
    @Query("UPDATE Door d SET d.locked = :locked " +
            "WHERE d.index = :index AND d.server.id = :serverId")
    int changeLockState(@Param("index") Integer index,
                        @Param("serverId") Integer serverId,
                        @Param("locked") Boolean locked);

    List<Door> findAllByServerId(Integer serverId);
}
