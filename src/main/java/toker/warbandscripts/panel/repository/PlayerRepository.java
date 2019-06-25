package toker.warbandscripts.panel.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import toker.warbandscripts.panel.entity.Player;


public interface PlayerRepository extends JpaRepository<Player, Integer> {
}
