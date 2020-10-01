package toker.panel.entity

import com.fasterxml.jackson.annotation.JsonView
import toker.panel.entity.pk.ProfessionAssignmentPK
import java.util.*
import javax.persistence.*

@Entity
@Table(name = "profession_assignment")
@IdClass(ProfessionAssignmentPK::class)
class ProfessionAssignment(
        @Id
        @ManyToOne
        @JoinColumn(name = "profession_id", referencedColumnName = "id", nullable = false)
        @JsonView(View.Profession::class)
        var profession: Profession? = null,

        @Id
        @ManyToOne
        @JoinColumn(name = "player_id", referencedColumnName = "id", nullable = false)
        @JsonView(View.Player::class)
        var player: Player? = null,

        @Column(name = "tier", nullable = false)
        var tier: Int? = null
) {
    interface View {
        interface Player
        interface Profession
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false
        other as ProfessionAssignment
        if (profession != other.profession) return false
        if (player != other.player) return false
        return true
    }

    override fun hashCode(): Int {
        var result = profession?.hashCode() ?: 0
        result = 31 * result + (player?.hashCode() ?: 0)
        return result
    }
}