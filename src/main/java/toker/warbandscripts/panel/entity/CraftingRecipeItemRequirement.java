package toker.warbandscripts.panel.entity;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "crafting_recipe_item_requirement", schema = "pax", catalog = "")
public class CraftingRecipeItemRequirement {
    private Integer id;
    private Integer amount;
    private CraftingRecipe craftingRecipeByRecipeId;
    private Item itemByItemId;

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
    @Column(name = "amount", nullable = false)
    public Integer getAmount() {
        return amount;
    }

    public void setAmount(Integer amount) {
        this.amount = amount;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CraftingRecipeItemRequirement that = (CraftingRecipeItemRequirement) o;
        return Objects.equals(id, that.id) &&
                Objects.equals(amount, that.amount);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, amount);
    }

    @ManyToOne
    @JoinColumn(name = "recipe_id", referencedColumnName = "id", nullable = false)
    public CraftingRecipe getCraftingRecipeByRecipeId() {
        return craftingRecipeByRecipeId;
    }

    public void setCraftingRecipeByRecipeId(CraftingRecipe craftingRecipeByRecipeId) {
        this.craftingRecipeByRecipeId = craftingRecipeByRecipeId;
    }

    @ManyToOne
    @JoinColumn(name = "item_id", referencedColumnName = "id", nullable = false)
    public Item getItemByItemId() {
        return itemByItemId;
    }

    public void setItemByItemId(Item itemByItemId) {
        this.itemByItemId = itemByItemId;
    }
}
