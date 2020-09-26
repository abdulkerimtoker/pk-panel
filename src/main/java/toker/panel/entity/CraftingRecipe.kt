package toker.panel.entity

import com.fasterxml.jackson.annotation.JsonIgnore
import com.fasterxml.jackson.annotation.JsonView
import toker.panel.entity.CraftingRecipe.View.ItemRequirements
import java.util.*
import javax.persistence.*

@Entity
@Table(name = "crafting_recipe")
data class CraftingRecipe(
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        @Column(name = "id", nullable = false)
        var id: Int? = null,

        @Column(name = "profession_tier", nullable = false)
        var professionTier: Int? = null,

        @Column(name = "price", nullable = false)
        var price: Int? = null,

        @ManyToOne
        @JoinColumns(JoinColumn(name = "station_index", referencedColumnName = "index", nullable = false), JoinColumn(name = "station_server_id", referencedColumnName = "server_id", nullable = false))
        @JsonView(View.CraftingStation::class)
        var craftingStation: CraftingStation? = null,

        @ManyToOne
        @JoinColumn(name = "profession_id", referencedColumnName = "id", nullable = false)
        @JsonView(View.Profession::class)
        var profession: Profession? = null,

        @ManyToOne
        @JoinColumn(name = "item_id", referencedColumnName = "id", nullable = false)
        @JsonView(View.Item::class)
        var item: Item? = null,

        @Column(name = "hours", nullable = false)
        var hours: Int? = null,

        @OneToMany(mappedBy = "craftingRecipe", cascade = [CascadeType.REMOVE])
        @JsonView(ItemRequirements::class)
        var itemRequirements: MutableSet<CraftingRecipeItemRequirement>? = null,

        @OneToMany(mappedBy = "craftingRecipe", cascade = [CascadeType.REMOVE])
        @JsonIgnore
        var craftingRequests: MutableSet<CraftingRequest>? = null
) {
    interface View {
        interface Item
        interface Profession
        interface CraftingStation
        interface ItemRequirements
    }
}