package toker.warbandscripts.panel.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import toker.warbandscripts.panel.entity.Player;

import java.util.List;


public interface PlayerRepository extends JpaRepository<Player, Integer>, JpaSpecificationExecutor<Player> {

    @Query("FROM Player " +
            "WHERE LOWER(" +
            "name) LIKE CONCAT('%',LOWER(?1),'%') " +
            "OR CAST(unique_id as char) LIKE CONCAT('%',?1,'%') " +
            "OR CAST(id as char) LIKE CONCAT('%',?1,'%')")
    List<Player> likeSearch(String likeString);
}
