package toker.panel.entity;

import com.fasterxml.jackson.annotation.JsonView;

import javax.persistence.*;
import java.sql.Timestamp;

@Entity
@Table(name = "crafting_request")
public class CraftingRequest {

    private Integer id;
    private Timestamp creationTime;
    private Boolean taken;
    private CraftingRecipe craftingRecipe;
    private Player player;
    private CraftingStationInstance craftingStationInstance;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    @Column(name = "creation_time", nullable = false)
    public Timestamp getCreationTime() {
        return creationTime;
    }

    public void setCreationTime(Timestamp creationTime) {
        this.creationTime = creationTime;
    }

    @Column(name = "is_taken", nullable = false)
    public Boolean isTaken() {
        return taken;
    }

    public void setTaken(Boolean taken) {
        this.taken = taken;
    }

    @ManyToOne
    @JoinColumn(name = "recipe_id", referencedColumnName = "id", nullable = false)
    @JsonView(View.CraftingRecipe.class)
    public CraftingRecipe getCraftingRecipe() {
        return craftingRecipe;
    }

    public void setCraftingRecipe(CraftingRecipe craftingRecipe) {
        this.craftingRecipe = craftingRecipe;
    }

    @ManyToOne
    @JoinColumn(name = "player_id", referencedColumnName = "id", nullable = false)
    @JsonView(View.Player.class)
    public Player getPlayer() {
        return player;
    }

    public void setPlayer(Player player) {
        this.player = player;
    }

    @ManyToOne
    @JoinColumn(name = "station_instance_id", referencedColumnName = "id", nullable = false)
    @JsonView(View.CraftingStationInstance.class)
    public CraftingStationInstance getCraftingStationInstance() {
        return craftingStationInstance;
    }

    public void setCraftingStationInstance(CraftingStationInstance craftingStationInstance) {
        this.craftingStationInstance = craftingStationInstance;
    }

    public interface View {
        interface CraftingStationInstance {}
        interface Player {}
        interface CraftingRecipe {}
    }
}
