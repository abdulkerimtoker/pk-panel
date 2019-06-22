package toker.warbandscripts.panel.entity;

import javax.persistence.*;

@Entity
@Table(name = "crafting_station_instance")
public class CraftingStationInstance {
    private Integer id;
    private String name;
    private CraftingStation craftingStationByStationId;

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

    @ManyToOne
    @JoinColumn(name = "station_id", referencedColumnName = "id", nullable = false)
    public CraftingStation getCraftingStationByStationId() {
        return craftingStationByStationId;
    }

    public void setCraftingStationByStationId(CraftingStation craftingStationByStationId) {
        this.craftingStationByStationId = craftingStationByStationId;
    }
}
