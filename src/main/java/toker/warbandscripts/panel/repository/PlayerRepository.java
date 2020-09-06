package toker.warbandscripts.panel.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;
import toker.warbandscripts.panel.entity.Player;
import toker.warbandscripts.panel.entity.Server;

import javax.persistence.LockModeType;
import java.util.List;
import java.util.Optional;


public interface PlayerRepository extends BaseRepository<Player, Integer> {
    @Query("SELECT p FROM Player p " +
            "WHERE (" +
            "LOWER(p.name) LIKE CONCAT('%',LOWER(?1),'%') " +
            "OR CAST(p.uniqueId as text) LIKE CONCAT('%',?1,'%') " +
            "OR CAST(p.id as text) LIKE CONCAT('%',?1,'%')" +
            ")" +
            "AND p.faction.server = ?2")
    List<Player> likeSearch(String likeString, Integer server);

    @Override
    @Lock(LockModeType.OPTIMISTIC_FORCE_INCREMENT)
    <S extends Player> S saveAndFlush(S s);
}
