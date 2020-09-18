package toker.panel.repository;

import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;
import toker.panel.entity.Player;

import javax.persistence.LockModeType;
import java.util.List;


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
