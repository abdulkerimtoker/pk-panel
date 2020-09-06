package toker.warbandscripts.panel.repository;

import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import toker.warbandscripts.panel.entity.Item;

import java.util.List;

public interface ItemRepository extends BaseRepository<Item, Integer> {
}
