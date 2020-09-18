package toker.panel.entity.pk;

import java.io.Serializable;
import java.util.Objects;

public class StartingItemPK implements Serializable {

    private Integer server;
    private Integer item;

    public Integer getServer() {
        return server;
    }

    public void setServer(Integer server) {
        this.server = server;
    }

    public Integer getItem() {
        return item;
    }

    public void setItem(Integer item) {
        this.item = item;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        StartingItemPK that = (StartingItemPK) o;
        return Objects.equals(server, that.server) &&
                Objects.equals(item, that.item);
    }

    @Override
    public int hashCode() {
        return Objects.hash(server, item);
    }
}
