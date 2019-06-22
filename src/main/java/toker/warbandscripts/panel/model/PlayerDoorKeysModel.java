package toker.warbandscripts.panel.model;

import toker.warbandscripts.panel.entity.DoorKey;
import toker.warbandscripts.panel.entity.Player;

import java.util.List;

public class PlayerDoorKeysModel {
    private List<DoorKey> doorKeys;
    private Player player;

    public List<DoorKey> getDoorKeys() {
        return doorKeys;
    }

    public void setDoorKeys(List<DoorKey> doorKeys) {
        this.doorKeys = doorKeys;
    }

    public Player getPlayer() {
        return player;
    }

    public void setPlayer(Player player) {
        this.player = player;
    }
}
