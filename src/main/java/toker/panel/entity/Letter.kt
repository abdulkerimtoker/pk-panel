package toker.panel.entity

import javax.persistence.*

@Entity
class Letter(
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    var id: Int? = null,

    @ManyToOne
    @JoinColumn(nullable = false)
    var server: Server,

    @ManyToOne
    @JoinColumn(nullable = false)
    var language: Language,

    @Column(nullable = false, length = 128)
    var title: String,

    @Column(nullable = false, length = 4096)
    var text: String = "",

    @Enumerated(EnumType.STRING)
    var sealState: SealState = SealState.NOT_SEALED
)

enum class SealState(val value: Int, val text: String) {
    NOT_SEALED(0, "Not Sealed"),
    SEALED(1, "Sealed"),
    UNSEALED(2, "Unsealed")
}