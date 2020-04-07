package toker.warbandscripts.panel.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.springframework.cache.annotation.Cacheable;

import javax.persistence.*;
import java.util.Collection;
import java.util.Objects;

@Entity
@Table(name = "profession")
public class Profession {
    private Integer id;
    private String name;
    private Integer max_tier;
    private Collection<ProfessionAssignment> professionAssignments;
    private Integer maxTier;

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
    @Column(name = "name", nullable = false, length = 32)
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Basic
    @Column(name = "max_tier", nullable = false)
    public Integer getMaxTier() {
        return max_tier;
    }

    public void setMaxTier(Integer max_tier) {
        this.max_tier = max_tier;
    }

    @OneToMany(mappedBy = "profession")
    @JsonIgnore
    public Collection<ProfessionAssignment> getProfessionAssignments() {
        return professionAssignments;
    }

    public void setProfessionAssignments(Collection<ProfessionAssignment> professionAssignments) {
        this.professionAssignments = professionAssignments;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Profession profession = (Profession) o;
        return Objects.equals(id, profession.id);
    }
}
