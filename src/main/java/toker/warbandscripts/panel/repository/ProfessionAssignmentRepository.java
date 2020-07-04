package toker.warbandscripts.panel.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.transaction.annotation.Transactional;
import toker.warbandscripts.panel.entity.ProfessionAssignment;

import java.util.List;

public interface ProfessionAssignmentRepository extends
        JpaRepository<ProfessionAssignment, Integer>,
        JpaSpecificationExecutor<ProfessionAssignment> {
    List<ProfessionAssignment> findAllByPlayerId(Integer playerId);

    @Modifying
    @Transactional
    void deleteByPlayerIdAndProfessionId(Integer playerId, Integer professionId);
}
