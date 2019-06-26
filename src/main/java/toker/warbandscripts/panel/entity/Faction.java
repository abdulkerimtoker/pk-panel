package toker.warbandscripts.panel.entity;

import com.fasterxml.jackson.annotation.JsonManagedReference;

import javax.persistence.*;
import java.util.Collection;
import java.util.Objects;

@Entity
@Table(name = "faction")
public class Faction {
    private Integer id;
    private String name;
    private Integer bannerId;
    private Collection<Player> playersById;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    @Basic
    @Column(name = "name", nullable = false, length = 64)
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Basic
    @Column(name = "banner_id", nullable = false)
    public Integer getBannerId() {
        return bannerId;
    }

    public void setBannerId(Integer bannerId) {
        this.bannerId = bannerId;
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

    @OneToMany(mappedBy = "factionByFactionId")
    @JsonManagedReference
    public Collection<Player> getPlayersById() {
        return playersById;
    }

    public void setPlayersById(Collection<Player> playersById) {
        this.playersById = playersById;
    }
}
