package toker.warbandscripts.panel.entity.pk;

import toker.warbandscripts.panel.entity.Server;

import java.io.Serializable;
import java.util.Objects;

public class NoticeBoardPK implements Serializable {

    private Integer index;
    private Integer server;

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
        NoticeBoardPK that = (NoticeBoardPK) o;
        return Objects.equals(index, that.index) &&
                Objects.equals(server, that.server);
    }

    @Override
    public int hashCode() {
        return Objects.hash(index, server);
    }
}
