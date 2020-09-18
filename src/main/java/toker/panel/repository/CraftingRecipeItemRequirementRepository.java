package toker.panel.repository;

import org.springframework.data.jpa.repository.Modifying;
import toker.panel.entity.CraftingRecipeItemRequirement;

import javax.transaction.Transactional;

public interface CraftingRecipeItemRequirementRepository
        extends BaseRepository<CraftingRecipeItemRequirement, Integer> {

    @Transactional
    @Modifying
    void deleteAllByCraftingRecipeId(Integer recipeId);
}
