package toker.warbandscripts.panel.entity;

import com.fasterxml.jackson.annotation.JsonView;

import javax.persistence.*;

@Entity
@Table(name = "crafting_station_instance")
public class CraftingStationInstance {
    private Integer id;
    private String name;
    private CraftingStation craftingStation;

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

    @ManyToOne
    @JoinColumns({
            @JoinColumn(name = "station_index", referencedColumnName = "index", nullable = false),
            @JoinColumn(name = "station_server_id", referencedColumnName = "server_id", nullable = false)
    })
    @JsonView(View.CraftingStation.class)
    public CraftingStation getCraftingStation() {
        return craftingStation;
    }

    public void setCraftingStation(CraftingStation craftingStationByStationId) {
        this.craftingStation = craftingStationByStationId;
    }

    public interface View {
        interface None {}
        interface CraftingStation {}
    }
}
