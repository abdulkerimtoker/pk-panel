package toker.warbandscripts.panel.repository;

import org.springframework.stereotype.Repository;
import toker.warbandscripts.panel.entity.AdminPermissions;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import java.util.List;

@Repository
@Transactional
public class PermissionRepository {

    @PersistenceContext
    private EntityManager em;

    public List<AdminPermissions> getPermissions() {
        return em.createQuery("FROM AdminPermissions").getResultList();
    }
}
