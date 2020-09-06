package toker.warbandscripts.panel.entity.pk;

import toker.warbandscripts.panel.entity.Server;

import java.io.Serializable;
import java.util.Objects;

public class DoorPK implements Serializable {

    private Integer index;
    private Integer server;

    public DoorPK() {}

    public DoorPK(Integer index, Integer server) {
        this.index = index;
        this.server = server;
    }

    public Integer getIndex() {
        return index;
    }

    public void setIndex(Integer index) {
        this.index = index;
    }

    public Integer getServer() {
        return server;
    }

    public void setServer(Integer server) {
        this.server = server;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        DoorPK doorPK = (DoorPK) o;
        return Objects.equals(index, doorPK.index) &&
                Objects.equals(server, doorPK.server);
    }

    @Override
    public int hashCode() {
        return Objects.hash(index, server);
    }
}
