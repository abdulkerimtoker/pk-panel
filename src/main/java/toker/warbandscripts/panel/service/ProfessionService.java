package toker.warbandscripts.panel.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import toker.warbandscripts.panel.entity.Profession;
import toker.warbandscripts.panel.entity.ProfessionAssignment;
import toker.warbandscripts.panel.repository.ProfessionAssignmentRepository;
import toker.warbandscripts.panel.repository.ProfessionRepository;

import java.util.List;

@Service
public class ProfessionService {

    @Autowired
    private ProfessionRepository professionRepository;
    @Autowired
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
}
