package toker.panel.repository

import org.springframework.data.jpa.repository.Modifying
import toker.panel.entity.CraftingRecipeItemRequirement
import javax.transaction.Transactional

interface CraftingRecipeItemRequirementRepository : BaseRepository<CraftingRecipeItemRequirement, Int> {
    @Transactional
    @Modifying
    fun deleteAllByCraftingRecipeId(recipeId: Int)
}