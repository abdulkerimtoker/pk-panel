package toker.warbandscripts.panel.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;
import toker.warbandscripts.panel.entity.Player;

import javax.persistence.LockModeType;
import java.util.List;


public interface PlayerRepository extends JpaRepository<Player, Integer>, JpaSpecificationExecutor<Player> {
    @Query("SELECT p FROM Player p " +
            "WHERE LOWER(p.name) LIKE CONCAT('%',LOWER(?1),'%') " +
            "OR CAST(p.uniqueId as char) LIKE CONCAT('%',?1,'%') " +
            "OR CAST(p.id as char) LIKE CONCAT('%',?1,'%')")
    List<Player> likeSearch(String likeString);

    @Override
    @Lock(LockModeType.OPTIMISTIC_FORCE_INCREMENT)
    <S extends Player> S saveAndFlush(S s);
}
