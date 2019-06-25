package toker.warbandscripts.panel.entity;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.Objects;

@Entity
@Table(name = "ban", schema = "pax", catalog = "")
public class Ban {
    private Integer id;
    private Integer playerUniqueId;
    private Timestamp time;
    private Timestamp expiryTime;
    private Boolean undone;
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

    @Basic
    @Column(name = "player_unique_id", nullable = false)
    public Integer getPlayerUniqueId() {
        return playerUniqueId;
    }

    public void setPlayerUniqueId(Integer playerUniqueId) {
        this.playerUniqueId = playerUniqueId;
    }

    @Basic
    @Column(name = "time", nullable = false)
    public Timestamp getTime() {
        return time;
    }

    public void setTime(Timestamp time) {
        this.time = time;
    }

    @Basic
    @Column(name = "expiry_time", nullable = true)
    public Timestamp getExpiryTime() {
        return expiryTime;
    }

    public void setExpiryTime(Timestamp expiryTime) {
        this.expiryTime = expiryTime;
    }

    @Basic
    @Column(name = "undone", nullable = false)
    public Boolean getUndone() {
        return undone;
    }

    public void setUndone(Boolean undone) {
        this.undone = undone;
    }

    @Basic
    @Column(name = "reason", nullable = false, length = 256)
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
        return Objects.equals(id, ban.id) &&
                Objects.equals(playerUniqueId, ban.playerUniqueId) &&
                Objects.equals(time, ban.time) &&
                Objects.equals(expiryTime, ban.expiryTime) &&
                Objects.equals(undone, ban.undone) &&
                Objects.equals(reason, ban.reason);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, playerUniqueId, time, expiryTime, undone, reason);
    }

    @ManyToOne
    @JoinColumn(name = "admin_id", referencedColumnName = "id", nullable = false)
    public PanelUser getPanelUser() {
        return panelUser;
    }

    public void setPanelUser(PanelUser panelUser) {
        this.panelUser = panelUser;
    }
}
