package toker.warbandscripts.panel.repository;

import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import toker.warbandscripts.panel.entity.Profession;

import java.util.List;

public interface ProfessionRepository extends BaseRepository<Profession, Integer> {
}
