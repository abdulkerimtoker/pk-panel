package toker.panel.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import toker.panel.entity.pk.FactionPK;

import javax.persistence.*;
import java.util.Collection;
import java.util.Objects;

@Entity
@Table(name = "faction")
@IdClass(FactionPK.class)
public class Faction {

    private Integer index;
    private Server server;

    private String name;
    private Integer bannerId;

    private Collection<Player> players;

    @Id
    @Column(name = "index", nullable = false)
    public Integer getIndex() {
        return index;
    }

    public void setIndex(Integer index) {
        this.index = index;
    }

    @Id
    @ManyToOne
    @JoinColumn(name = "server_id", referencedColumnName = "id", nullable = false)
    public Server getServer() {
        return server;
    }

    public void setServer(Server server) {
        this.server = server;
    }

    @Column(name = "name", nullable = false, length = 64)
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Column(name = "banner_id", nullable = false)
    public Integer getBannerId() {
        return bannerId;
    }

    public void setBannerId(Integer bannerId) {
        this.bannerId = bannerId;
    }

    @OneToMany(mappedBy = "faction")
    @JsonBackReference
    public Collection<Player> getPlayers() {
        return players;
    }

    public void setPlayers(Collection<Player> players) {
        this.players = players;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Faction faction = (Faction) o;
        return Objects.equals(index, faction.index) &&
                Objects.equals(server, faction.server);
    }

    @Override
    public int hashCode() {
        return Objects.hash(index, server, name, bannerId);
    }

    public interface View {
        interface None {}
    }
}
