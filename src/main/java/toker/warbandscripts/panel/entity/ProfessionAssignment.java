package toker.warbandscripts.panel.entity;

import com.fasterxml.jackson.annotation.JsonView;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;

@Entity
@Table(name = "profession_assignment")
@IdClass(ProfessionAssignmentPK.class)
public class ProfessionAssignment {
    private Profession profession;
    private Player player;
    private Integer tier;

    @Id
    @ManyToOne
    @JoinColumn(name = "profession_id", referencedColumnName = "id", nullable = false)
    @JsonView(View.Profession.class)
    public Profession getProfession() {
        return profession;
    }

    public void setProfession(Profession profession) {
        this.profession = profession;
    }

    @Id
    @ManyToOne
    @JoinColumn(name = "player_id", referencedColumnName = "id", nullable = false)
    @JsonView(View.Player.class)
    public Player getPlayer() {
        return player;
    }

    public void setPlayer(Player player) {
        this.player = player;
    }

    public Integer getTier() {
        return tier;
    }

    public void setTier(Integer tier) {
        this.tier = tier;
    }

    @Override
    public int hashCode() {
        return Objects.hash(profession, player);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ProfessionAssignment that = (ProfessionAssignment) o;
        return Objects.equals(profession, that.profession) &&
                Objects.equals(player, that.player) &&
                Objects.equals(tier, that.tier);
    }

    public static class View {
        public static class Player {}
        public static class Profession {}
    }
}

class ProfessionAssignmentPK implements Serializable {
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