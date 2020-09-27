package toker.panel.entity

import com.fasterxml.jackson.annotation.JsonView
import toker.panel.entity.CraftingStation.View.Recipes
import toker.panel.entity.pk.CraftingStationPK
import java.util.*
import javax.persistence.*

@Entity
@Table(name = "crafting_station")
@IdClass(CraftingStationPK::class)
data class CraftingStation(
        @Id
        @Column(name = "index", nullable = false)
        var index: Int? = null,

        @Id
        @ManyToOne
        @JoinColumn(name = "server_id", referencedColumnName = "id", nullable = false)
        var server: Server? = null,

        @Column(name = "name", nullable = false, length = 64)
        var name: String? = null,
) {
    @OneToMany(mappedBy = "craftingStation")
    @JsonView(Recipes::class)
    var craftingRecipes: MutableSet<CraftingRecipe>? = null

    @OneToMany(mappedBy = "craftingStation")
    @JsonView(View.Instances::class)
    var instances: MutableSet<CraftingStationInstance>? = null

    interface View {
        interface None
        interface Recipes
        interface Instances
    }
}