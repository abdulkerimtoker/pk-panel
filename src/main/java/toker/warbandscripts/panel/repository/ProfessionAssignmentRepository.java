package toker.warbandscripts.panel.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import toker.warbandscripts.panel.entity.ProfessionAssignment;

import java.util.List;

public interface ProfessionAssignmentRepository extends
        JpaRepository<ProfessionAssignment, Integer>,
        JpaSpecificationExecutor<ProfessionAssignment> {
    List<ProfessionAssignment> findAllByPlayerId(Integer playerId);
}
