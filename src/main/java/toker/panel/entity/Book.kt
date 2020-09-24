package toker.panel.entity

import javax.persistence.*

@Entity
@Table(name = "book")
data class Book(
        @Column(name = "id", nullable = false)
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        @Id
        var id: Int? = null,

        @Column(name = "name", nullable = false, length = 64)
        var name: String? = null,

        @JoinColumn(name = "server_id", referencedColumnName = "id", nullable = false)
        @ManyToOne
        var server: Server? = null,

        @OneToMany(mappedBy = "book")
        var bookPages: Collection<BookPage>? = null
)