package toker.warbandscripts.panel.model;

import toker.warbandscripts.panel.entity.Inventory;
import toker.warbandscripts.panel.entity.InventorySlot;
import toker.warbandscripts.panel.entity.Item;

import java.util.List;

public class PlayerInventoryModel {
    private Integer playerId;
    private String playerName;
    private List<InventorySlot> inventorySlots;
    private List<Item> items;

    public Integer getPlayerId() {
        return playerId;
    }

    public void setPlayerId(Integer playerId) {
        this.playerId = playerId;
    }

    public String getPlayerName() {
        return playerName;
    }

    public void setPlayerName(String playerName) {
        this.playerName = playerName;
    }

    public List<InventorySlot> getInventorySlots() {
        return inventorySlots;
    }

    public void setInventorySlots(List<InventorySlot> inventorySlots) {
        this.inventorySlots = inventorySlots;
    }

    public List<Item> getItems() {
        return items;
    }

    public void setItems(List<Item> items) {
        this.items = items;
    }
}
