package toker.panel.entity

import com.fasterxml.jackson.annotation.JsonIgnore
import javax.persistence.*

@Entity
@Table(name = "item_type")
class ItemType(
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        @Column(name = "id", nullable = false)
        var id: Int? = null,

        @Column(name = "name", nullable = false, length = 16)
        var name: String? = null
) {
    @OneToMany(mappedBy = "type")
    @JsonIgnore
    var items: MutableSet<Item>? = null
}