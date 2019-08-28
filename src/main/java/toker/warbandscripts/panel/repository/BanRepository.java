package toker.warbandscripts.panel.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import toker.warbandscripts.panel.entity.Ban;

import java.util.List;

public interface BanRepository extends JpaRepository<Ban, Integer>, JpaSpecificationExecutor<Ban> {
    List<Ban> findAllByPlayerUniqueId(Integer playerUniqueId);
}
