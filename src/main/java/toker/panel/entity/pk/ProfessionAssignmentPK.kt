package toker.panel.entity.pk;

import java.io.Serializable;
import java.util.Objects;

public class ProfessionAssignmentPK implements Serializable {
    private Integer profession;
    private Integer player;

    public Integer getProfession() {
        return profession;
    }

    public void setProfession(Integer profession) {
        this.profession = profession;
    }

    public Integer getPlayer() {
        return player;
    }

    public void setPlayer(Integer player) {
        this.player = player;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        ProfessionAssignmentPK that = (ProfessionAssignmentPK)obj;
        return Objects.equals(this.profession, that.profession)
                && Objects.equals(this.player, that.player);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.profession, this.player);
    }
}
