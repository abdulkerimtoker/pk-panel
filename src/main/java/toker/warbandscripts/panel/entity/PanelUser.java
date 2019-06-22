package toker.warbandscripts.panel.entity;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.Collection;
import java.util.Objects;

@Entity
@Table(name = "panel_user", schema = "pax", catalog = "")
public class PanelUser {
    private Integer id;
    private String username;
    private String password;
    private Timestamp creationTime;
    private Boolean isLocked;
    private Collection<Ban> bansById;
    private PanelUserRank panelUserRankByRankId;
    private Collection<PanelUserAuthorityAssignment> panelUserAuthorityAssignmentsById;

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
    @Column(name = "username", nullable = false, length = 32)
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    @Basic
    @Column(name = "password", nullable = false, length = 32)
    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Basic
    @Column(name = "creation_time", nullable = false)
    public Timestamp getCreationTime() {
        return creationTime;
    }

    public void setCreationTime(Timestamp creationTime) {
        this.creationTime = creationTime;
    }

    @Basic
    @Column(name = "is_locked", nullable = false)
    public Boolean getIsLocked() {
        return isLocked;
    }

    public void setIsLocked(Boolean isLocked) {
        this.isLocked = isLocked;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PanelUser panelUser = (PanelUser) o;
        return Objects.equals(id, panelUser.id) &&
                Objects.equals(username, panelUser.username) &&
                Objects.equals(password, panelUser.password) &&
                Objects.equals(creationTime, panelUser.creationTime) &&
                Objects.equals(isLocked, panelUser.isLocked);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, username, password, creationTime, isLocked);
    }

    @OneToMany(mappedBy = "panelUserByAdminId")
    public Collection<Ban> getBansById() {
        return bansById;
    }

    public void setBansById(Collection<Ban> bansById) {
        this.bansById = bansById;
    }

    @ManyToOne
    @JoinColumn(name = "rank_id", referencedColumnName = "id", nullable = false)
    public PanelUserRank getPanelUserRankByRankId() {
        return panelUserRankByRankId;
    }

    public void setPanelUserRankByRankId(PanelUserRank panelUserRankByRankId) {
        this.panelUserRankByRankId = panelUserRankByRankId;
    }

    @OneToMany(mappedBy = "panelUserByPanelUserId", fetch = FetchType.EAGER)
    public Collection<PanelUserAuthorityAssignment> getPanelUserAuthorityAssignmentsById() {
        return panelUserAuthorityAssignmentsById;
    }

    public void setPanelUserAuthorityAssignmentsById(Collection<PanelUserAuthorityAssignment> panelUserAuthorityAssignmentsById) {
        this.panelUserAuthorityAssignmentsById = panelUserAuthorityAssignmentsById;
    }

    @Transient
    public boolean hasAuthority(PanelUserAuthority authority) {
        for (PanelUserAuthorityAssignment assignment : getPanelUserAuthorityAssignmentsById()) {
            if (assignment.getPanelUserAuthorityByAuthorityId().getId() == authority.getId()) {
                return true;
            }
        }
        return false;
    }
}
