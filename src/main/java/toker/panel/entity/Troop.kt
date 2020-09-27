package toker.panel.entity

import com.fasterxml.jackson.annotation.JsonIgnore
import javax.persistence.*

@Entity
@Table(name = "troop")
class Troop(
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        @Column(name = "id", nullable = false)
        var id: Int? = null,

        @Column(name = "name", nullable = false, length = 64)
        var name: String? = null,
) {
    @OneToMany(mappedBy = "troop")
    @JsonIgnore
    var players: MutableSet<Player>? = null
}