package toker.warbandscripts.panel.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import toker.warbandscripts.panel.entity.Inventory;
import toker.warbandscripts.panel.entity.InventorySlot;
import toker.warbandscripts.panel.entity.Player;
import toker.warbandscripts.panel.repository.InventoryRepository;
import toker.warbandscripts.panel.repository.InventorySlotRepository;
import toker.warbandscripts.panel.repository.ItemRepository;
import toker.warbandscripts.panel.repository.PlayerRepository;

import java.lang.reflect.InvocationTargetException;
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

    public PlayerService(PlayerRepository playerRepository,
                         InventoryRepository inventoryRepository,
                         InventorySlotRepository inventorySlotRepository) {
        this.playerRepository = playerRepository;
        this.inventoryRepository = inventoryRepository;
        this.inventorySlotRepository = inventorySlotRepository;
    }

    public Optional<Player> getPlayer(int id) {
        return playerRepository.findById(id);
    }

    public Player savePlayer(Player player) {
        return playerRepository.save(player);
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
            } catch (IllegalAccessException e) {
                e.printStackTrace();
                return false;
            } catch (InvocationTargetException e) {
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

    public boolean updatePlayerInventorySlot(int inventoryId, InventorySlot inventorySlot) {
        InventorySlot current = inventorySlotRepository.findByInventoryIdAndSlot(inventoryId, inventorySlot.getSlot())
                .orElse(null);
        if (current != null) {
            return inventorySlotRepository.updateSlot(inventorySlot.getInventory(), inventorySlot.getSlot(),
                    inventorySlot.getItem(), inventorySlot.getAmmo())
                    == 1;
        }
        return false;
    }
}
