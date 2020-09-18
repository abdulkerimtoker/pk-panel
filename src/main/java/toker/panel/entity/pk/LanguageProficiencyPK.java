package toker.panel.entity.pk;

import java.io.Serializable;
import java.util.Objects;

public class LanguageProficiencyPK implements Serializable {

    private Integer player;
    private Integer language;

    public Integer getPlayer() {
        return player;
    }

    public void setPlayer(Integer player) {
        this.player = player;
    }

    public Integer getLanguage() {
        return language;
    }

    public void setLanguage(Integer language) {
        this.language = language;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        LanguageProficiencyPK that = (LanguageProficiencyPK) o;
        return Objects.equals(player, that.player) &&
                Objects.equals(language, that.language);
    }

    @Override
    public int hashCode() {
        return Objects.hash(player, language);
    }
}
