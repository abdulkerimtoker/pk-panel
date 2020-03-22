package toker.warbandscripts.panel.entity;

import com.fasterxml.jackson.annotation.JsonView;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.Objects;

@Entity
@Table(name = "ban")
public class Ban {
    private Integer id;
    private Integer playerUniqueId;
    private Timestamp time;
    private Boolean isUndone;
    private Boolean isPermanent;
    private String reason;
    private PanelUser panelUser;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    @Column(name = "player_unique_id", nullable = false)
    public Integer getPlayerUniqueId() {
        return playerUniqueId;
    }

    public void setPlayerUniqueId(Integer playerUniqueId) {
        this.playerUniqueId = playerUniqueId;
    }

    @Column(name = "time", nullable = false)
    public Timestamp getTime() {
        return time;
    }

    public void setTime(Timestamp time) {
        this.time = time;
    }

    @Column(name = "is_undone", nullable = false)
    public Boolean getUndone() {
        return isUndone;
    }

    public void setUndone(Boolean undone) {
        isUndone = undone;
    }

    @Column(name = "is_permanent", nullable = false)
    public Boolean getPermanent() {
        return isPermanent;
    }

    public void setPermanent(Boolean permanent) {
        isPermanent = permanent;
    }

    @Column(name = "reason", nullable = false, length = 512)
    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Ban ban = (Ban) o;
        return Objects.equals(id, ban.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, playerUniqueId, time, isUndone, isPermanent, reason);
    }

    @ManyToOne
    @JoinColumn(name = "admin_id", referencedColumnName = "id", nullable = false)
    @JsonView(Ban.View.PanelUser.class)
    public PanelUser getPanelUser() {
        return panelUser;
    }

    public void setPanelUser(PanelUser panelUser) {
        this.panelUser = panelUser;
    }

    public class View {
        public class PanelUser {}
        public class None {}
    }
}
