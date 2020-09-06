package toker.warbandscripts.panel.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonView;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.Collection;
import java.util.Objects;

@Entity
@Table(name = "panel_user")
public class PanelUser {
    private Integer id;
    private String username;
    private String claimedIdentity;
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

    @Column(name = "username", nullable = false, unique = true, length = 64)
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    @Column(name = "claimed_identity", unique = true, length = 128)
    public String getClaimedIdentity() {
        return claimedIdentity;
    }

    public void setClaimedIdentity(String claimedIdentity) {
        this.claimedIdentity = claimedIdentity;
    }

    @Column(name = "creation_time", nullable = false)
    public Timestamp getCreationTime() {
        return creationTime;
    }

    public void setCreationTime(Timestamp creationTime) {
        this.creationTime = creationTime;
    }

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
        return Objects.equals(id, panelUser.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, username, claimedIdentity, creationTime, isLocked);
    }

    @OneToMany(mappedBy = "panelUser")
    @JsonView(View.Bans.class)
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
    @JsonView(View.AuthorityAssignments.class)
    public Collection<PanelUserAuthorityAssignment> getAuthorityAssignments() {
        return authorityAssignments;
    }

    public void setAuthorityAssignments(Collection<PanelUserAuthorityAssignment> authorityAssignments) {
        this.authorityAssignments = authorityAssignments;
    }

    public interface View {
        interface Bans {}
        interface AuthorityAssignments {}
    }
}
