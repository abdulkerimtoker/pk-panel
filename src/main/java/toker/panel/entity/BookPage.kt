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
)