package toker.panel.entity

import com.fasterxml.jackson.annotation.JsonView
import java.sql.Timestamp
import javax.persistence.*

@Entity
@Table(name = "crafting_request")
class CraftingRequest(
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        @Column(name = "id", nullable = false)
        var id: Int? = null,

        @Column(name = "creation_time", nullable = false)
        var creationTime: Timestamp? = null,

        @Column(name = "is_taken", nullable = false)
        var isTaken: Boolean? = null,

        @ManyToOne
        @JoinColumn(name = "recipe_id", referencedColumnName = "id", nullable = false)
        @JsonView(View.CraftingRecipe::class)
        var craftingRecipe: CraftingRecipe? = null,

        @ManyToOne
        @JoinColumn(name = "player_id", referencedColumnName = "id", nullable = false)
        @JsonView(View.Player::class)
        var player: Player? = null,

        @ManyToOne
        @JoinColumn(name = "station_instance_id", referencedColumnName = "id", nullable = false)
        @JsonView(View.CraftingStationInstance::class)
        var craftingStationInstance: CraftingStationInstance? = null
) {
    interface View {
        interface CraftingStationInstance
        interface Player
        interface CraftingRecipe
    }
}