package toker.warbandscripts.panel.entity;

import com.fasterxml.jackson.annotation.JsonView;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "door_key")
public class DoorKey {
    private Integer id;
    private Boolean isOwner;
    private Door door;
    private Player player;

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
    @Column(name = "is_owner", nullable = false)
    public Boolean getIsOwner() {
        return isOwner;
    }

    public void setIsOwner(Boolean isOwner) {
        this.isOwner = isOwner;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        DoorKey doorKey = (DoorKey) o;
        return Objects.equals(id, doorKey.id) &&
                Objects.equals(isOwner, doorKey.isOwner);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, isOwner);
    }

    @ManyToOne
    @JoinColumn(name = "door_id", referencedColumnName = "id", nullable = false)
    @JsonView(View.Door.class)
    public Door getDoor() {
        return door;
    }

    public void setDoor(Door door) {
        this.door = door;
    }

    @ManyToOne
    @JoinColumn(name = "user_id", referencedColumnName = "id", nullable = false)
    @JsonView(View.Player.class)
    public Player getPlayer() {
        return player;
    }

    public void setPlayer(Player player) {
        this.player = player;
    }

    public static class View {
        public static class Player extends toker.warbandscripts.panel.entity.Door.View.DoorKeys {}
        public static class Door {}
    }
}
