package toker.panel.entity

import com.fasterxml.jackson.annotation.JsonView
import toker.panel.entity.Door.View.DoorKeys
import toker.panel.entity.pk.DoorPK
import java.util.*
import javax.persistence.*

@Entity
@Table(name = "door")
@IdClass(DoorPK::class)
data class Door(
        @Id
        @Column(name = "index", nullable = false)
        var index: Int? = null,

        @Id
        @ManyToOne
        @JoinColumn(name = "server_id", referencedColumnName = "id", nullable = false)
        var server: Server? = null,

        @Column(name = "name", nullable = false, length = 32)
        var name: String? = null,

        @Column(name = "locked", nullable = false)
        var locked: Boolean? = null,

        @OneToMany(mappedBy = "door")
        @JsonView(DoorKeys::class)
        var doorKeys: MutableSet<DoorKey>? = null
) {
    interface View {
        interface DoorKeys
    }
}