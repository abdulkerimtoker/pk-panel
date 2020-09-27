package toker.panel.entity

import com.fasterxml.jackson.annotation.JsonView
import toker.panel.entity.Door.View.DoorKeys
import java.util.*
import javax.persistence.*

@Entity
@Table(name = "door_key")
class DoorKey(
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        @Column(name = "id", nullable = false)
        var id: Int? = null,

        @Column(name = "is_owner", nullable = false)
        var isOwner: Boolean? = false,

        @ManyToOne
        @JoinColumns(JoinColumn(name = "door_index", referencedColumnName = "index", nullable = false), JoinColumn(name = "door_server_id", referencedColumnName = "server_id", nullable = false))
        @JsonView(View.Door::class)
        var door: Door? = null,

        @ManyToOne
        @JoinColumn(name = "user_id", referencedColumnName = "id", nullable = false)
        @JsonView(View.Player::class)
        var player: Player? = null
) {
    interface View {
        interface Player
        interface Door
    }
}