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
}