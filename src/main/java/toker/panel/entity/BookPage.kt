package toker.panel.entity

import java.util.*
import javax.persistence.*

@Entity
@Table(name = "book_page")
class BookPage(
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        @Column(name = "id", nullable = false)
        var id: Int? = null,

        @Column(name = "page_number", nullable = false)
        var pageNumber: Int? = null,

        @Column(name = "content", nullable = false, length = 2048)
        var content: String? = null,

        @ManyToOne
        @JoinColumn(name = "book_id", referencedColumnName = "id", nullable = false)
        var book: Book? = null
) {
        override fun equals(other: Any?): Boolean {
                if (this === other) return true
                if (javaClass != other?.javaClass) return false
                other as BookPage
                if (id != other.id) return false
                return true
        }

        override fun hashCode(): Int {
                return id ?: 0
        }
}