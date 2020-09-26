package toker.panel.entity

import javax.persistence.*

@Entity
@Table(name = "item")
data class Item(
        @Id
        @Column(name = "id", nullable = false)
        var id: Int? = null,

        @Column(name = "code_name", nullable = false, length = 32)
        var codeName: String? = null,

        @Column(name = "name", nullable = false, length = 32)
        var name: String? = null,

        @ManyToOne
        @JoinColumn(name = "type", referencedColumnName = "id", nullable = false)
        var type: ItemType? = null
)