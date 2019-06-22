/*package toker.warbandscripts.panel.entity;

import javax.persistence.*;
import java.util.Collection;
import java.util.Objects;

@Entity
@Table(name = "profession")
public class Profession {
    private Integer id;
    private String name;
    private Integer max_tier;
    private Collection<ProfessionAssignment> professionAssignmentsById;

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
    @Column(name = "name", nullable = false, length = 64)
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

    @OneToMany(mappedBy = "professionByProfessionId")
    public Collection<ProfessionAssignment> getProfessionAssignmentsById() {
        return professionAssignmentsById;
    }

    public void setProfessionAssignmentsById(Collection<ProfessionAssignment> professionAssignmentsById) {
        this.professionAssignmentsById = professionAssignmentsById;
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
*/