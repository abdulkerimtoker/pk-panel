package toker.warbandscripts.panel.model;

import toker.warbandscripts.panel.entity.Ban;
import toker.warbandscripts.panel.entity.Player;

import java.util.Collection;
import java.util.Date;
import java.util.List;

public class PlayerBansModel {
    private Player player;
    private Collection<Ban> activeBans;
    private Collection<Ban> pastBans;

    public Player getPlayer() {
        return player;
    }

    public void setPlayer(Player player) {
        this.player = player;
    }

    public Collection<Ban> getActiveBans() {
        return activeBans;
    }

    public void setActiveBans(Collection<Ban> activeBans) {
        this.activeBans = activeBans;
    }

    public Collection<Ban> getPastBans() {
        return pastBans;
    }

    public void setPastBans(Collection<Ban> pastBans) {
        this.pastBans = pastBans;
    }
}
