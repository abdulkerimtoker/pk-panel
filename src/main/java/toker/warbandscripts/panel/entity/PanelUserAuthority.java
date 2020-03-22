package toker.warbandscripts.panel.entity;

import org.springframework.security.core.GrantedAuthority;

import javax.persistence.*;
import java.util.Collection;
import java.util.Objects;

@Entity
@Table(name = "panel_user_authority")
public class PanelUserAuthority {
    private Integer id;
    private String authorityName;
    private String authorityDescription;

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
    @Column(name = "authority_name", nullable = false, unique = true, length = 64)
    public String getAuthorityName() {
        return authorityName;
    }

    public void setAuthorityName(String authorityName) {
        this.authorityName = authorityName;
    }

    @Basic
    @Column(name = "authority_description", nullable = false, length = 128)
    public String getAuthorityDescription() {
        return authorityDescription;
    }

    public void setAuthorityDescription(String authorityDescription) {
        this.authorityDescription = authorityDescription;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PanelUserAuthority that = (PanelUserAuthority) o;
        return Objects.equals(id, that.id) &&
                Objects.equals(authorityName, that.authorityName) &&
                Objects.equals(authorityDescription, that.authorityDescription);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, authorityName, authorityDescription);
    }
}
