package toker.warbandscripts.panel.entity;

import com.fasterxml.jackson.annotation.JsonView;

import javax.persistence.*;
import java.util.Collection;
import java.util.Objects;

@Entity
@Table(name = "crafting_recipe")
public class CraftingRecipe {
    private Integer id;
    private Integer professionTier;
    private Integer price;
    private CraftingStation craftingStation;
    private Profession profession;
    private Item item;
    private Integer hours;
    private Collection<CraftingRecipeItemRequirement> craftingRecipeItemRequirements;

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
    @Column(name = "profession_tier", nullable = false)
    public Integer getProfessionTier() {
        return professionTier;
    }

    public void setProfessionTier(Integer professionTier) {
        this.professionTier = professionTier;
    }

    @Basic
    @Column(name = "price", nullable = false)
    public Integer getPrice() {
        return price;
    }

    public void setPrice(Integer price) {
        this.price = price;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CraftingRecipe that = (CraftingRecipe) o;
        return Objects.equals(id, that.id) &&
                Objects.equals(professionTier, that.professionTier) &&
                Objects.equals(price, that.price);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, professionTier, price);
    }

    @ManyToOne
    @JoinColumn(name = "station_id", referencedColumnName = "id", nullable = false)
    @JsonView(View.CraftingStation.class)
    public CraftingStation getCraftingStation() {
        return craftingStation;
    }

    public void setCraftingStation(CraftingStation craftingStationByStationId) {
        this.craftingStation = craftingStationByStationId;
    }

    @ManyToOne
    @JoinColumn(name = "profession_id", referencedColumnName = "id", nullable = false)
    @JsonView(View.Profession.class)
    public Profession getProfession() {
        return profession;
    }

    public void setProfession(Profession professionByProfessionId) {
        this.profession = professionByProfessionId;
    }

    @ManyToOne
    @JoinColumn(name = "item_id", referencedColumnName = "id", nullable = false)
    @JsonView(View.Item.class)
    public Item getItem() {
        return item;
    }

    public void setItem(Item itemByItemId) {
        this.item = itemByItemId;
    }

    @Basic
    @Column(name = "hours", nullable = false)
    public Integer getHours() {
        return hours;
    }

    public void setHours(Integer hours) {
        this.hours = hours;
    }

    @OneToMany(mappedBy = "craftingRecipeByRecipeId")
    @JsonView(View.ItemRequirements.class)
    public Collection<CraftingRecipeItemRequirement> getCraftingRecipeItemRequirements() {
        return craftingRecipeItemRequirements;
    }

    public void setCraftingRecipeItemRequirements(Collection<CraftingRecipeItemRequirement> craftingRecipeItemRequirementsById) {
        this.craftingRecipeItemRequirements = craftingRecipeItemRequirementsById;
    }

    public interface View {
        interface Item {}
        interface Profession {}
        interface CraftingStation {}
        interface ItemRequirements {}
    }
}
