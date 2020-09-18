package toker.panel.entity;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "panel_user_authority_assignment")
public class PanelUserAuthorityAssignment {
    private Integer id;
    private PanelUser panelUser;
    private Server server;
    private PanelUserAuthority authority;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    @ManyToOne
    @JoinColumn(name = "panel_user_id", referencedColumnName = "id", nullable = false)
    public PanelUser getPanelUser() {
        return panelUser;
    }

    public void setPanelUser(PanelUser panelUser) {
        this.panelUser = panelUser;
    }

    @ManyToOne
    @JoinColumn(name = "server_id", referencedColumnName = "id", nullable = false)
    public Server getServer() {
        return server;
    }

    public void setServer(Server server) {
        this.server = server;
    }

    @ManyToOne
    @JoinColumn(name = "authority_id", referencedColumnName = "id", nullable = false)
    public PanelUserAuthority getAuthority() {
        return authority;
    }

    public void setAuthority(PanelUserAuthority authority) {
        this.authority = authority;
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
}
