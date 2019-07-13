package toker.warbandscripts.panel.entity;

import javax.persistence.*;
import java.util.Collection;
import java.util.Objects;

@Entity
@Table(name = "crafting_station")
public class CraftingStation {
    private Integer id;
    private String name;
    private Collection<CraftingRecipe> craftingRecipesById;
    private Collection<CraftingStationInstance> craftingStationInstances;

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
        CraftingStation that = (CraftingStation) o;
        return Objects.equals(id, that.id) &&
                Objects.equals(name, that.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name);
    }

    @OneToMany(mappedBy = "craftingStationByStationId")
    public Collection<CraftingRecipe> getCraftingRecipesById() {
        return craftingRecipesById;
    }

    public void setCraftingRecipesById(Collection<CraftingRecipe> craftingRecipesById) {
        this.craftingRecipesById = craftingRecipesById;
    }

    @OneToMany(mappedBy = "craftingStationByStationId")
    public Collection<CraftingStationInstance> getCraftingStationInstances() {
        return craftingStationInstances;
    }

    public void setCraftingStationInstances(Collection<CraftingStationInstance> craftingStationInstances) {
        this.craftingStationInstances = craftingStationInstances;
    }
}
