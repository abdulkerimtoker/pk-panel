package toker.warbandscripts.panel.entity;

import javax.persistence.*;
import java.util.Collection;
import java.util.Objects;

@Entity
@Table(name = "crafting_recipe", schema = "pax", catalog = "")
public class CraftingRecipe {
    private Integer id;
    private Integer professionTier;
    private Integer price;
    private CraftingStation craftingStationByStationId;
    private Profession professionByProfessionId;
    private Item itemByItemId;
    private Integer hours;
    private Collection<CraftingRecipeItemRequirement> craftingRecipeItemRequirementsById;

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
    public CraftingStation getCraftingStationByStationId() {
        return craftingStationByStationId;
    }

    public void setCraftingStationByStationId(CraftingStation craftingStationByStationId) {
        this.craftingStationByStationId = craftingStationByStationId;
    }

    @ManyToOne
    @JoinColumn(name = "profession_id", referencedColumnName = "id", nullable = false)
    public Profession getProfessionByProfessionId() {
        return professionByProfessionId;
    }

    public void setProfessionByProfessionId(Profession professionByProfessionId) {
        this.professionByProfessionId = professionByProfessionId;
    }

    @ManyToOne
    @JoinColumn(name = "item_id", referencedColumnName = "id", nullable = false)
    public Item getItemByItemId() {
        return itemByItemId;
    }

    public void setItemByItemId(Item itemByItemId) {
        this.itemByItemId = itemByItemId;
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
    public Collection<CraftingRecipeItemRequirement> getCraftingRecipeItemRequirementsById() {
        return craftingRecipeItemRequirementsById;
    }

    public void setCraftingRecipeItemRequirementsById(Collection<CraftingRecipeItemRequirement> craftingRecipeItemRequirementsById) {
        this.craftingRecipeItemRequirementsById = craftingRecipeItemRequirementsById;
    }
}
