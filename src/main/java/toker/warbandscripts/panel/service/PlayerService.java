package toker.warbandscripts.panel.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import toker.warbandscripts.panel.entity.*;
import toker.warbandscripts.panel.repository.*;

import java.lang.reflect.InvocationTargetException;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

@Service
public class PlayerService {

    @Autowired
    private PlayerRepository playerRepository;
    @Autowired
    private InventoryRepository inventoryRepository;
    @Autowired
    private InventorySlotRepository inventorySlotRepository;
    @Autowired
    private DoorKeyRepository doorKeyRepository;
    @Autowired
    private NoticeBoadAccessRepository noticeBoadAccessRepository;
    @Autowired
    private ProfessionAssignmentRepository professionAssignmentRepository;

    public PlayerService(PlayerRepository playerRepository,
                         InventoryRepository inventoryRepository,
                         InventorySlotRepository inventorySlotRepository,
                         DoorKeyRepository doorKeyRepository,
                         NoticeBoadAccessRepository noticeBoadAccessRepository,
                         ProfessionAssignmentRepository professionAssignmentRepository) {
        this.playerRepository = playerRepository;
        this.inventoryRepository = inventoryRepository;
        this.inventorySlotRepository = inventorySlotRepository;
        this.doorKeyRepository = doorKeyRepository;
        this.noticeBoadAccessRepository = noticeBoadAccessRepository;
        this.professionAssignmentRepository = professionAssignmentRepository;
    }

    public Optional<Player> getPlayer(int id) {
        return playerRepository.findById(id);
    }

    public Player savePlayer(Player player) {
        return playerRepository.saveAndFlush(player);
    }

    public List<Player> searchPlayers(String searchTerm) {
        return playerRepository.likeSearch(searchTerm);
    }

    public boolean setPlayerField(int id, String field, Object value) {
        Player player = getPlayer(id).orElse(null);
        if (player != null) {
            String setterName = String.format("set%s", Character.toUpperCase(field.charAt(0)) + field.substring(1).toLowerCase());
            try {
                Player.class.getDeclaredMethod(setterName, value.getClass())
                    .invoke(player, value);
            } catch (NoSuchMethodException e) {
                return false;
            } catch (IllegalAccessException | InvocationTargetException e) {
                e.printStackTrace();
                return false;
            }
            playerRepository.save(player);
            return true;
        }
        return false;
    }

    public Inventory getPlayerInventory(int playerId) {
        return inventoryRepository.findFirstByPlayerId(playerId);
    }

    public InventorySlot saveInventorySlot(InventorySlot inventorySlot) {
        return inventorySlotRepository.saveAndFlush(inventorySlot);
    }

    public List<DoorKey> getPlayerDoorKeys(int playerId) {
        return doorKeyRepository.findAllByPlayerId(playerId);
    }

    public List<NoticeBoardAccess> getPlayerBoardAccesses(int playerId) {
        return noticeBoadAccessRepository.findAllByPlayerId(playerId);
    }

    public List<ProfessionAssignment> getPlayerProfessions(int playerId) {
        return professionAssignmentRepository.findAllByPlayerId(playerId);
    }
}
