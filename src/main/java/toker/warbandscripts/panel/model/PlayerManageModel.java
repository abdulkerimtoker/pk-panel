package toker.warbandscripts.panel.model;

import toker.warbandscripts.panel.entity.Faction;
import toker.warbandscripts.panel.entity.Item;
import toker.warbandscripts.panel.entity.Player;
import toker.warbandscripts.panel.entity.Troop;

import java.util.List;

public class PlayerManageModel {
    private Player player;
    private List<Item> handItems;
    private List<Item> headArmors;
    private List<Item> bodyArmors;
    private List<Item> footArmors;
    private List<Item> handArmors;
    private List<Item> horses;
    private List<Troop> troops;
    private List<Faction> factions;

    public Player getPlayer() {
        return player;
    }

    public void setPlayer(Player player) {
        this.player = player;
    }

    public List<Item> getHandItems() {
        return handItems;
    }

    public void setHandItems(List<Item> handItems) {
        this.handItems = handItems;
    }

    public List<Item> getHeadArmors() {
        return headArmors;
    }

    public void setHeadArmors(List<Item> headArmors) {
        this.headArmors = headArmors;
    }

    public List<Item> getBodyArmors() {
        return bodyArmors;
    }

    public void setBodyArmors(List<Item> bodyArmors) {
        this.bodyArmors = bodyArmors;
    }

    public List<Item> getFootArmors() {
        return footArmors;
    }

    public void setFootArmors(List<Item> footArmors) {
        this.footArmors = footArmors;
    }

    public List<Item> getHandArmors() {
        return handArmors;
    }

    public void setHandArmors(List<Item> handArmors) {
        this.handArmors = handArmors;
    }

    public List<Item> getHorses() {
        return horses;
    }

    public void setHorses(List<Item> horses) {
        this.horses = horses;
    }

    public List<Troop> getTroops() {
        return troops;
    }

    public void setTroops(List<Troop> troops) {
        this.troops = troops;
    }

    public List<Faction> getFactions() {
        return factions;
    }

    public void setFactions(List<Faction> factions) {
        this.factions = factions;
    }
}
