package toker.panel.service;

import org.springframework.stereotype.Service;
import toker.panel.entity.Profession;
import toker.panel.entity.ProfessionAssignment;
import toker.panel.repository.ProfessionAssignmentRepository;
import toker.panel.repository.ProfessionRepository;

import java.util.List;

@Service
public class ProfessionService {

    private ProfessionRepository professionRepository;
    private ProfessionAssignmentRepository professionAssignmentRepository;

    public ProfessionService(ProfessionRepository professionRepository,
                             ProfessionAssignmentRepository professionAssignmentRepository) {
        this.professionRepository = professionRepository;
        this.professionAssignmentRepository = professionAssignmentRepository;
    }

    public List<Profession> getAllProfessions() {
        return professionRepository.findAll();
    }

    public ProfessionAssignment saveProfessionAssignment(ProfessionAssignment professionAssignment) {
        return professionAssignmentRepository.saveAndFlush(professionAssignment);
    }

    public void revokeProfession(int playerId, int professionId) {
        professionAssignmentRepository.deleteByPlayerIdAndProfessionId(playerId, professionId);
    }
}
