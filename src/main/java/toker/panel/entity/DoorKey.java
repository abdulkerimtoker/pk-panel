package toker.panel.entity;

import com.fasterxml.jackson.annotation.JsonView;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "door_key")
public class DoorKey {

    private Integer id;
    private Boolean isOwner = false;
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

    @Column(name = "is_owner", nullable = false)
    public Boolean getIsOwner() {
        return isOwner;
    }

    public void setIsOwner(Boolean isOwner) {
        this.isOwner = isOwner;
    }

    @ManyToOne
    @JoinColumns({
            @JoinColumn(name = "door_index", referencedColumnName = "index", nullable = false),
            @JoinColumn(name = "door_server_id", referencedColumnName = "server_id", nullable = false)
    })
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        DoorKey doorKey = (DoorKey) o;
        return Objects.equals(id, doorKey.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, isOwner);
    }

    public static class View {
        public static class Player extends toker.panel.entity.Door.View.DoorKeys {}
        public static class Door {}
    }
}
