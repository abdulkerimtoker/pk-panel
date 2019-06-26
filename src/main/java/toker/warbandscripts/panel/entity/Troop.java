package toker.warbandscripts.panel.entity;

import com.fasterxml.jackson.annotation.JsonManagedReference;

import javax.persistence.*;
import java.util.Collection;
import java.util.Objects;

@Entity
@Table(name = "troop", schema = "pax", catalog = "")
public class Troop {
    private Integer id;
    private String name;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Troop troop = (Troop) o;
        return Objects.equals(id, troop.id) &&
                Objects.equals(name, troop.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name);
    }

    @OneToMany(mappedBy = "troopByTroopId")
    @JsonManagedReference
    public Collection<Player> getPlayersById() {
        return playersById;
    }

    public void setPlayersById(Collection<Player> playersById) {
        this.playersById = playersById;
    }
}
