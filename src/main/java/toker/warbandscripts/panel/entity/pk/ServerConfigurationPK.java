package toker.warbandscripts.panel.entity.pk;

import toker.warbandscripts.panel.entity.Server;

import java.io.Serializable;
import java.util.Objects;

public class ServerConfigurationPK implements Serializable {

    private Server server;
    private String name;

    public Server getServer() {
        return server;
    }

    public void setServer(Server server) {
        this.server = server;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ServerConfigurationPK that = (ServerConfigurationPK) o;
        return Objects.equals(server, that.server) &&
                Objects.equals(name, that.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(server, name);
    }
}
