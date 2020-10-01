package toker.panel.entity

import com.fasterxml.jackson.annotation.JsonIgnore
import toker.panel.entity.pk.CraftingRecipeItemRequirementPK
import javax.persistence.*

@Entity
@Table(name = "crafting_recipe_item_requirement")
@IdClass(CraftingRecipeItemRequirementPK::class)
class CraftingRecipeItemRequirement(
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
) {
        override fun equals(other: Any?): Boolean {
                if (this === other) return true
                if (javaClass != other?.javaClass) return false
                other as CraftingRecipeItemRequirement
                if (craftingRecipe != other.craftingRecipe) return false
                if (item != other.item) return false
                return true
        }

        override fun hashCode(): Int {
                var result = craftingRecipe?.hashCode() ?: 0
                result = 31 * result + (item?.hashCode() ?: 0)
                return result
        }
}