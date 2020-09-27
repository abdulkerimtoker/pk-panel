package toker.panel.repository

import org.springframework.data.jpa.repository.Modifying
import org.springframework.transaction.annotation.Transactional
import toker.panel.entity.ProfessionAssignment

interface ProfessionAssignmentRepository : BaseRepository<ProfessionAssignment, Int> {
    fun findAllByPlayerId(playerId: Int): List<ProfessionAssignment>

    @Modifying
    @Transactional
    fun deleteByPlayerIdAndProfessionId(playerId: Int, professionId: Int)
}