package toker.warbandscripts.panel.entity;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.Collection;
import java.util.Objects;

@Entity
@Table(name = "panel_user")
@NamedEntityGraph(name = "PanelUser.detail", attributeNodes = @NamedAttributeNode("authorityAssignments"))
public class PanelUser {
    private Integer id;
    private String username;
    private String password;
    private Timestamp creationTime;
    private Boolean isLocked;
    private Collection<Ban> bans;
    private PanelUserRank rank;
    private Collection<PanelUserAuthorityAssignment> authorityAssignments;

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

    @OneToMany(mappedBy = "panelUser")
    public Collection<Ban> getBans() {
        return bans;
    }

    public void setBans(Collection<Ban> bans) {
        this.bans = bans;
    }

    @ManyToOne
    @JoinColumn(name = "rank_id", referencedColumnName = "id", nullable = false)
    public PanelUserRank getRank() {
        return rank;
    }

    public void setRank(PanelUserRank rank) {
        this.rank = rank;
    }

    @OneToMany(mappedBy = "panelUser")
    public Collection<PanelUserAuthorityAssignment> getAuthorityAssignments() {
        return authorityAssignments;
    }

    public void setAuthorityAssignments(Collection<PanelUserAuthorityAssignment> authorityAssignments) {
        this.authorityAssignments = authorityAssignments;
    }

    @Transient
    public boolean hasAuthority(PanelUserAuthority authority) {
        for (PanelUserAuthorityAssignment assignment : getAuthorityAssignments()) {
            if (assignment.getAuthority().getId() == authority.getId()) {
                return true;
            }
        }
        return false;
    }
}
