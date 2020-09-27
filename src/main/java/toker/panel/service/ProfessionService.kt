package toker.panel.service

import org.springframework.stereotype.Service
import toker.panel.entity.Profession
import toker.panel.repository.ProfessionRepository
import toker.panel.repository.ProfessionAssignmentRepository
import toker.panel.entity.ProfessionAssignment

@Service
class ProfessionService(private val professionRepository: ProfessionRepository,
                        private val professionAssignmentRepository: ProfessionAssignmentRepository) {
    val allProfessions: List<Profession>
        get() = professionRepository.findAll()

    fun saveProfessionAssignment(professionAssignment: ProfessionAssignment): ProfessionAssignment {
        return professionAssignmentRepository.saveAndFlush(professionAssignment)
    }

    fun revokeProfession(playerId: Int, professionId: Int) {
        professionAssignmentRepository.deleteByPlayerIdAndProfessionId(playerId, professionId)
    }
}