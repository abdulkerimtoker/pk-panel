package toker.warbandscripts.panel.entity;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "panel_user_authority_assignment", schema = "pax", catalog = "")
public class PanelUserAuthorityAssignment {
    private Integer id;
    private PanelUser panelUserByPanelUserId;
    private PanelUserAuthority panelUserAuthorityByAuthorityId;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PanelUserAuthorityAssignment that = (PanelUserAuthorityAssignment) o;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @ManyToOne
    @JoinColumn(name = "panel_user_id", referencedColumnName = "id", nullable = false)
    public PanelUser getPanelUserByPanelUserId() {
        return panelUserByPanelUserId;
    }

    public void setPanelUserByPanelUserId(PanelUser panelUserByPanelUserId) {
        this.panelUserByPanelUserId = panelUserByPanelUserId;
    }

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "authority_id", referencedColumnName = "id", nullable = false)
    public PanelUserAuthority getPanelUserAuthorityByAuthorityId() {
        return panelUserAuthorityByAuthorityId;
    }

    public void setPanelUserAuthorityByAuthorityId(PanelUserAuthority panelUserAuthorityByAuthorityId) {
        this.panelUserAuthorityByAuthorityId = panelUserAuthorityByAuthorityId;
    }
}
