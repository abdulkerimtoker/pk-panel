package toker.panel.service

import org.springframework.stereotype.Service
import toker.panel.entity.CraftingStation
import toker.panel.repository.CraftingRequestRepository
import toker.panel.repository.CraftingStationRepository

@Service
class CraftingService(private val craftingStationRepository: CraftingStationRepository,
                      private val craftingRequestRepository: CraftingRequestRepository) {
    fun getCraftingStations(serverId: Int): List<CraftingStation> {
        return craftingStationRepository.findAllByServerId(serverId)
    }
}