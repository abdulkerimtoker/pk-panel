package toker.warbandscripts.panel.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import org.springframework.cache.annotation.Cacheable;

import javax.persistence.*;
import java.util.Collection;
import java.util.Objects;

@Entity
@Table(name = "faction")
public class Faction {
    private Integer id;
    private String name;
    private Integer bannerId;
    private Server server;
    private Collection<Player> players;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
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

    @ManyToOne
    @JoinColumn(name = "server_id", referencedColumnName = "id", nullable = false)
    public Server getServer() {
        return server;
    }

    public void setServer(Server server) {
        this.server = server;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Faction faction = (Faction) o;
        return Objects.equals(id, faction.id) &&
                Objects.equals(name, faction.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name);
    }

    @OneToMany(mappedBy = "faction")
    @JsonBackReference
    public Collection<Player> getPlayers() {
        return players;
    }

    public void setPlayers(Collection<Player> players) {
        this.players = players;
    }
}
