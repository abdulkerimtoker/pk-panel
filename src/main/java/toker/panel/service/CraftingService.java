package toker.panel.service;

import org.springframework.stereotype.Service;
import toker.panel.entity.CraftingStation;
import toker.panel.repository.CraftingRequestRepository;
import toker.panel.repository.CraftingStationRepository;

import java.util.List;

@Service
public class CraftingService {

    private CraftingStationRepository craftingStationRepository;
    private CraftingRequestRepository craftingRequestRepository;

    public CraftingService(CraftingStationRepository craftingStationRepository,
                           CraftingRequestRepository craftingRequestRepository) {
        this.craftingStationRepository = craftingStationRepository;
        this.craftingRequestRepository = craftingRequestRepository;
    }

    public List<CraftingStation> getCraftingStations(int serverId) {
        return craftingStationRepository.findAllByServerId(serverId);
    }
}
