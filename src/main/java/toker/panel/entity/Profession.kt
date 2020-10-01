package toker.panel.entity

import com.fasterxml.jackson.annotation.JsonIgnore
import javax.persistence.*

@Entity
@Table(name = "profession")
class Profession(
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        @Column(name = "id", nullable = false)
        var id: Int? = null,

        @Column(name = "name", nullable = false, length = 32)
        var name: String? = null,
) {
    @OneToMany(mappedBy = "profession")
    @JsonIgnore
    var professionAssignments: MutableSet<ProfessionAssignment>? = null

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false
        other as Profession
        if (id != other.id) return false
        return true
    }

    override fun hashCode(): Int {
        return id ?: 0
    }
}