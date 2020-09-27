package toker.panel.entity

import com.fasterxml.jackson.annotation.JsonView
import javax.persistence.*

@Entity
@Table(name = "crafting_station_instance")
class CraftingStationInstance(
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        @Column(name = "id", nullable = false)
        var id: Int? = null,

        @Column(name = "name", nullable = false, length = 64)
        var name: String? = null,

        @ManyToOne
        @JoinColumns(JoinColumn(name = "station_index", referencedColumnName = "index", nullable = false), JoinColumn(name = "station_server_id", referencedColumnName = "server_id", nullable = false))
        @JsonView(View.CraftingStation::class)
        var craftingStation: CraftingStation? = null
) {
    interface View {
        interface None
        interface CraftingStation
    }
}