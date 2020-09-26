package toker.panel.entity.pk

import java.io.Serializable

data class CraftingRecipeItemRequirementPK(
        var craftingRecipe: Int? = null,
        var item: Int? = null
) : Serializable