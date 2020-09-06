package toker.warbandscripts.panel.entity.pk;

import java.io.Serializable;
import java.util.Objects;

public class AdminPermissionsPK implements Serializable {

    private Integer uniqueId;
    private Integer server;

    public Integer getUniqueId() {
        return uniqueId;
    }

    public void setUniqueId(Integer uniqueId) {
        this.uniqueId = uniqueId;
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
        AdminPermissionsPK that = (AdminPermissionsPK) o;
        return Objects.equals(uniqueId, that.uniqueId) &&
                Objects.equals(server, that.server);
    }

    @Override
    public int hashCode() {
        return Objects.hash(uniqueId, server);
    }
}
