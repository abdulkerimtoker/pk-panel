package toker.panel.entity.pk;

import java.io.Serializable;
import java.util.Objects;

public class ServerStartupCommandPK implements Serializable {

    private Integer server;
    private String command;

    public Integer getServer() {
        return server;
    }

    public void setServer(Integer server) {
        this.server = server;
    }

    public String getCommand() {
        return command;
    }

    public void setCommand(String command) {
        this.command = command;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ServerStartupCommandPK that = (ServerStartupCommandPK) o;
        return Objects.equals(server, that.server) &&
                Objects.equals(command, that.command);
    }

    @Override
    public int hashCode() {
        return Objects.hash(server, command);
    }
}
