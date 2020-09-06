package toker.warbandscripts.panel.entity.pk;

import java.io.Serializable;
import java.util.Objects;

public class CraftingRecipeItemRequirementPK implements Serializable {

    private Integer craftingRecipe;
    private Integer item;

    public CraftingRecipeItemRequirementPK() {}

    public CraftingRecipeItemRequirementPK(Integer craftingRecipe, Integer item) {
        this.craftingRecipe = craftingRecipe;
        this.item = item;
    }

    public Integer getCraftingRecipe() {
        return craftingRecipe;
    }

    public void setCraftingRecipe(Integer craftingRecipe) {
        this.craftingRecipe = craftingRecipe;
    }

    public Integer getItem() {
        return item;
    }

    public void setItem(Integer item) {
        this.item = item;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CraftingRecipeItemRequirementPK that = (CraftingRecipeItemRequirementPK) o;
        return Objects.equals(craftingRecipe, that.craftingRecipe) &&
                Objects.equals(item, that.item);
    }

    @Override
    public int hashCode() {
        return Objects.hash(craftingRecipe, item);
    }
}
