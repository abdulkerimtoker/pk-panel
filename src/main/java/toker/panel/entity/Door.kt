package toker.panel.entity

import com.fasterxml.jackson.annotation.JsonView
import toker.panel.entity.Door.View.DoorKeys
import toker.panel.entity.pk.DoorPK
import java.util.*
import javax.persistence.*

@Entity
@Table(name = "door")
@IdClass(DoorPK::class)
class Door(
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
        var locked: Boolean? = null
) {
    @OneToMany(mappedBy = "door")
    @JsonView(DoorKeys::class)
    var doorKeys: MutableSet<DoorKey>? = null

    interface View {
        interface DoorKeys
        interface None
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false
        other as Door
        if (index != other.index) return false
        if (server != other.server) return false
        return true
    }

    override fun hashCode(): Int {
        var result = index ?: 0
        result = 31 * result + (server?.hashCode() ?: 0)
        return result
    }
}