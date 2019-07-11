package toker.warbandscripts.panel.entity;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;

@Entity
@Table(name = "profession_assignment")
@IdClass(ProfessionAssignmentPK.class)
public class ProfessionAssignment {
    private Profession professionByProfessionId;
    private Player playerByPlayerId;
    private Integer tier;

    @Id
    @ManyToOne
    @JoinColumn(name = "profession_id", referencedColumnName = "id", nullable = false)
    public Profession getProfessionByProfessionId() {
        return professionByProfessionId;
    }

    public void setProfessionByProfessionId(Profession professionByProfessionId) {
        this.professionByProfessionId = professionByProfessionId;
    }

    @Id
    @ManyToOne
    @JoinColumn(name = "player_id", referencedColumnName = "id", nullable = false)
    public Player getPlayerByPlayerId() {
        return playerByPlayerId;
    }

    public void setPlayerByPlayerId(Player playerByPlayerId) {
        this.playerByPlayerId = playerByPlayerId;
    }

    public Integer getTier() {
        return tier;
    }

    public void setTier(Integer tier) {
        this.tier = tier;
    }

    @Override
    public int hashCode() {
        return Objects.hash(professionByProfessionId, playerByPlayerId);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ProfessionAssignment that = (ProfessionAssignment) o;
        return Objects.equals(professionByProfessionId, that.professionByProfessionId) &&
                Objects.equals(playerByPlayerId, that.playerByPlayerId) &&
                Objects.equals(tier, that.tier);
    }
}

class ProfessionAssignmentPK implements Serializable {
    private Integer professionByProfessionId;
    private Integer playerByPlayerId;

    public Integer getProfessionByProfessionId() {
        return professionByProfessionId;
    }

    public void setProfessionByProfessionId(Integer professionByProfessionId) {
        this.professionByProfessionId = professionByProfessionId;
    }

    public Integer getPlayerByPlayerId() {
        return playerByPlayerId;
    }

    public void setPlayerByPlayerId(Integer playerByPlayerId) {
        this.playerByPlayerId = playerByPlayerId;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        ProfessionAssignmentPK that = (ProfessionAssignmentPK)obj;
        return Objects.equals(this.professionByProfessionId, that.professionByProfessionId)
                && Objects.equals(this.playerByPlayerId, that.playerByPlayerId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.professionByProfessionId, this.playerByPlayerId);
    }
}