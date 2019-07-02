package toker.warbandscripts.panel.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import toker.warbandscripts.panel.entity.Player;

import java.util.List;


public interface PlayerRepository extends JpaRepository<Player, Integer>, JpaSpecificationExecutor<Player> {
    List<Player> likeSearch(String likeString);
}
