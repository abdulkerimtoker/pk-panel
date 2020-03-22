package toker.warbandscripts.panel.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;
import toker.warbandscripts.panel.entity.Ban;

import java.util.List;

public interface BanRepository extends JpaRepository<Ban, Integer>, JpaSpecificationExecutor<Ban> {
    List<Ban> findAllByPlayerUniqueId(Integer playerUniqueId);
    List<Ban> findAllByPanelUserId(Integer panelUserId);

    @Modifying
    @Transactional
    @Query("UPDATE Ban SET isUndone = true WHERE playerUniqueId = ?1")
    void undoAllByPlayerUniqueId(Integer playerUniqueId);
}
