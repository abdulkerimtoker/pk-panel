package toker.warbandscripts.panel.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.Mapping;
import toker.warbandscripts.panel.entity.Ban;

import javax.annotation.Generated;
import java.util.List;

public interface BanRepository extends BaseRepository<Ban, Integer> {
    List<Ban> findAllByPlayerUniqueId(Integer playerUniqueId);
    List<Ban> findAllByPanelUserId(Integer panelUserId);
    List<Ban> findAllByServerId(Integer serverId);

    @Modifying
    @Transactional
    @Query("UPDATE Ban SET undone = true WHERE playerUniqueId = :playerUniqueId " +
            "AND server.id = :serverId")
    void undoAllByPlayerUniqueId(Integer playerUniqueId, Integer serverId);
}
