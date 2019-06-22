package toker.warbandscripts.panel.model;

import toker.warbandscripts.panel.entity.Faction;
import toker.warbandscripts.panel.entity.ServerConfiguration;

import java.util.List;

public class ServerModel {
    private boolean isServerOn;
    private boolean errorOccured;
    private boolean isAutoStart;
    private List<Faction> factions;
    private List<ServerConfiguration> configurations;

    public boolean isServerOn() {
        return isServerOn;
    }

    public void setServerOn(boolean serverOn) {
        isServerOn = serverOn;
    }

    public boolean isErrorOccured() {
        return errorOccured;
    }

    public void setErrorOccured(boolean errorOccured) {
        this.errorOccured = errorOccured;
    }

    public boolean isAutoStart() {
        return isAutoStart;
    }

    public void setAutoStart(boolean autoStart) {
        isAutoStart = autoStart;
    }

    public List<Faction> getFactions() {
        return factions;
    }

    public void setFactions(List<Faction> factions) {
        this.factions = factions;
    }

    public List<ServerConfiguration> getConfigurations() {
        return configurations;
    }

    public void setConfigurations(List<ServerConfiguration> configurations) {
        this.configurations = configurations;
    }
}
