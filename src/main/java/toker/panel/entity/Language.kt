package toker.panel.entity

import javax.persistence.*

@Entity
@Table(name = "language")
data class Language(
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        @Column(name = "id")
        var id: Int? = null,

        @Column(name = "name", nullable = false, unique = true)
        var name: String? = null
)