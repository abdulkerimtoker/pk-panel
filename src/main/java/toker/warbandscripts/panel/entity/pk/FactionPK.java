package toker.warbandscripts.panel.entity.pk;

import java.io.Serializable;
import java.util.Objects;

public class FactionPK implements Serializable  {

    private Integer index;
    private Integer server;

    public FactionPK() {
    }

    public FactionPK(Integer index, Integer server) {
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
        FactionPK factionPK = (FactionPK) o;
        return Objects.equals(index, factionPK.index) &&
                Objects.equals(server, factionPK.server);
    }

    @Override
    public int hashCode() {
        return Objects.hash(index, server);
    }
}
