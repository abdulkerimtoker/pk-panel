package toker.panel.entity

import com.fasterxml.jackson.annotation.JsonView
import toker.panel.entity.pk.LanguageProficiencyPK
import javax.persistence.*

@Entity
@Table(name = "language_proficiency")
@IdClass(LanguageProficiencyPK::class)
class LanguageProficiency(
        @Id
        @ManyToOne
        @JoinColumn(name = "player_id", referencedColumnName = "id")
        @JsonView(View.Player::class)
        var player: Player? = null,

        @Id
        @ManyToOne
        @JoinColumn(name = "language_id", referencedColumnName = "id")
        @JsonView(View.Language::class)
        var language: Language? = null
) {
    interface View {
        interface None
        interface Language
        interface Player
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false
        other as LanguageProficiency
        if (player != other.player) return false
        if (language != other.language) return false
        return true
    }

    override fun hashCode(): Int {
        var result = player?.hashCode() ?: 0
        result = 31 * result + (language?.hashCode() ?: 0)
        return result
    }
}