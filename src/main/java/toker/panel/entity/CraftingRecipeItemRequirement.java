package toker.panel.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import toker.panel.entity.pk.CraftingRecipeItemRequirementPK;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "crafting_recipe_item_requirement")
@IdClass(CraftingRecipeItemRequirementPK.class)
public class CraftingRecipeItemRequirement {

    private CraftingRecipe craftingRecipe;
    private Item item;
    private Integer amount;

    @Id
    @ManyToOne
    @JoinColumn(name = "recipe_id", referencedColumnName = "id", nullable = false)
    @JsonIgnore
    public CraftingRecipe getCraftingRecipe() {
        return craftingRecipe;
    }

    public void setCraftingRecipe(CraftingRecipe craftingRecipeByRecipeId) {
        this.craftingRecipe = craftingRecipeByRecipeId;
    }

    @Id
    @ManyToOne
    @JoinColumn(name = "item_id", referencedColumnName = "id", nullable = false)
    public Item getItem() {
        return item;
    }

    public void setItem(Item itemByItemId) {
        this.item = itemByItemId;
    }

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
        return Objects.equals(craftingRecipe, that.craftingRecipe) &&
                Objects.equals(item, that.item);
    }

    @Override
    public int hashCode() {
        return Objects.hash(craftingRecipe, item);
    }
}
