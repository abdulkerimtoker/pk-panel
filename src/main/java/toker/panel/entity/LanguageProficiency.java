package toker.panel.entity;

import com.fasterxml.jackson.annotation.JsonView;
import toker.panel.entity.pk.LanguageProficiencyPK;

import javax.persistence.*;

@Entity
@Table(name = "language_proficiency")
@IdClass(LanguageProficiencyPK.class)
public class LanguageProficiency {

    private Player player;
    private Language language;

    @Id
    @ManyToOne
    @JoinColumn(name = "player_id", referencedColumnName = "id")
    @JsonView(View.Player.class)
    public Player getPlayer() {
        return player;
    }

    public void setPlayer(Player player) {
        this.player = player;
    }

    @Id
    @ManyToOne
    @JoinColumn(name = "language_id", referencedColumnName = "id")
    @JsonView(View.Language.class)
    public Language getLanguage() {
        return language;
    }

    public void setLanguage(Language language) {
        this.language = language;
    }

    public interface View {
        interface None {}
        interface Language {}
        interface Player {}
    }
}
