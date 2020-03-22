package toker.warbandscripts.panel.service;

import org.springframework.stereotype.Service;
import toker.warbandscripts.panel.entity.CraftingRequest;
import toker.warbandscripts.panel.repository.CraftingRequestRepository;

import java.util.List;

@Service
public class CraftingService {

    private CraftingRequestRepository craftingRequestRepository;

    public CraftingService(CraftingRequestRepository craftingRequestRepository) {
        this.craftingRequestRepository = craftingRequestRepository;
    }

}
