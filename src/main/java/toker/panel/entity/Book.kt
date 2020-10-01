package toker.panel.entity

import javax.persistence.*

@Entity
@Table(name = "book")
class Book(
        @Column(name = "id", nullable = false)
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        @Id
        var id: Int? = null,

        @Column(name = "name", nullable = false, length = 64)
        var name: String? = null,

        @ManyToOne
        @JoinColumn(name = "server_id", referencedColumnName = "id", nullable = false)
        var server: Server? = null,
) {
    @OneToMany(mappedBy = "book")
    var bookPages: MutableSet<BookPage>? = null

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false
        other as Book
        if (id != other.id) return false
        return true
    }

    override fun hashCode(): Int {
        return id ?: 0
    }
}