package toker.panel.entity

import javax.persistence.*

@Entity
@Table(name = "item")
class Item(
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
) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false
        other as Item
        if (id != other.id) return false
        return true
    }

    override fun hashCode(): Int {
        return id ?: 0
    }
}