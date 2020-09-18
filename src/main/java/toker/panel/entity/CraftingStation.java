package toker.panel.entity;

import com.fasterxml.jackson.annotation.JsonView;
import toker.panel.entity.pk.CraftingStationPK;

import javax.persistence.*;
import java.util.Collection;
import java.util.Objects;

@Entity
@Table(name = "crafting_station")
@IdClass(CraftingStationPK.class)
public class CraftingStation {

    private Integer index;
    private Server server;
    private String name;
    private Collection<CraftingRecipe> craftingRecipes;
    private Collection<CraftingStationInstance> instances;

    @Id
    @Column(name = "index", nullable = false)
    public Integer getIndex() {
        return index;
    }

    public void setIndex(Integer id) {
        this.index = id;
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

    @OneToMany(mappedBy = "craftingStation")
    @JsonView(View.Recipes.class)
    public Collection<CraftingRecipe> getCraftingRecipes() {
        return craftingRecipes;
    }

    public void setCraftingRecipes(Collection<CraftingRecipe> craftingRecipesById) {
        this.craftingRecipes = craftingRecipesById;
    }

    @OneToMany(mappedBy = "craftingStation")
    @JsonView(View.Instances.class)
    public Collection<CraftingStationInstance> getInstances() {
        return instances;
    }

    public void setInstances(Collection<CraftingStationInstance> craftingStationInstances) {
        this.instances = craftingStationInstances;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CraftingStation that = (CraftingStation) o;
        return Objects.equals(index, that.index) &&
                Objects.equals(name, that.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(index, name);
    }

    public interface View {
        interface None {}
        interface Recipes {}
        interface Instances {}
    }
}

