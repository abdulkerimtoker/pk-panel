package toker.panel.entity

import com.fasterxml.jackson.annotation.JsonIgnore
import toker.panel.entity.pk.CraftingRecipeItemRequirementPK
import javax.persistence.*

@Entity
@Table(name = "crafting_recipe_item_requirement")
@IdClass(CraftingRecipeItemRequirementPK::class)
data class CraftingRecipeItemRequirement(
        @Id
        @ManyToOne
        @JoinColumn(name = "recipe_id", referencedColumnName = "id", nullable = false)
        @JsonIgnore
        var craftingRecipe: CraftingRecipe? = null,

        @Id
        @ManyToOne
        @JoinColumn(name = "item_id", referencedColumnName = "id", nullable = false)
        var item: Item? = null,

        @Column(name = "amount", nullable = false)
        var amount: Int? = null
)