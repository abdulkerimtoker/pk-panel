package toker.warbandscripts.panel.entity;

import com.fasterxml.jackson.annotation.JsonView;

import javax.persistence.*;
import java.util.Collection;
import java.util.Objects;

@Entity
@Table(name = "panel_user_rank")
public class PanelUserRank {
    private Integer id;
    private String rankName;
    private String description;
    private Collection<PanelUser> panelUsers;

    @Id
    @Column(name = "id", nullable = false)
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    @Basic
    @Column(name = "rank_name", nullable = false, length = 32)
    public String getRankName() {
        return rankName;
    }

    public void setRankName(String rankName) {
        this.rankName = rankName;
    }

    @Basic
    @Column(name = "description", nullable = false, length = 64)
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PanelUserRank that = (PanelUserRank) o;
        return Objects.equals(id, that.id) &&
                Objects.equals(rankName, that.rankName) &&
                Objects.equals(description, that.description);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, rankName, description);
    }

    @OneToMany(mappedBy = "rank")
    @JsonView(View.PanelUsers.class)
    public Collection<PanelUser> getPanelUsers() {
        return panelUsers;
    }

    public void setPanelUsers(Collection<PanelUser> panelUsers) {
        this.panelUsers = panelUsers;
    }

    public class View {
        public class PanelUsers {}
    }
}
