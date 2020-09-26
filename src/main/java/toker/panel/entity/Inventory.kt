package toker.panel.entity

import com.fasterxml.jackson.annotation.JsonIgnore
import java.util.*
import javax.persistence.*

@Entity
@Table(name = "inventory")
data class Inventory(
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        @Column(name = "id", nullable = false)
        var id: Int? = null,

        @Column(name = "size", nullable = false)
        var size: Int? = null,

        @ManyToOne
        @JoinColumn(name = "player_id", referencedColumnName = "id", nullable = false)
        @JsonIgnore
        var player: Player? = null,

        @OneToMany(mappedBy = "inventory", cascade = [CascadeType.MERGE, CascadeType.PERSIST])
        var slots: MutableSet<InventorySlot>? = null
)