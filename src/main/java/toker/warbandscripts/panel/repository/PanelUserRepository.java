package toker.warbandscripts.panel.repository;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import toker.warbandscripts.panel.entity.PanelUser;
import toker.warbandscripts.panel.entity.PanelUserAuthority;
import toker.warbandscripts.panel.entity.PanelUserAuthorityAssignment;
import toker.warbandscripts.panel.entity.PanelUserRank;
import toker.warbandscripts.panel.service.PanelUserService;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import java.sql.Timestamp;
import java.time.Instant;
import java.util.LinkedList;
import java.util.List;

@Repository
@Transactional
public class PanelUserRepository {

    @PersistenceContext
    private EntityManager em;

    private PanelUserService panelUserService;

    @Autowired
    public PanelUserRepository(PanelUserService panelUserService) {
        this.panelUserService = panelUserService;
    }

    public PanelUser findPanelUserByUsername(String username) {
        try {
            return (PanelUser) em.createQuery("FROM PanelUser WHERE username = :username")
                    .setParameter("username", username)
                    .getSingleResult();
        } catch (NoResultException ex) {
            return null;
        }
    }

    public PanelUser findPanelUserById(int panelUserId) {
        return em.find(PanelUser.class, panelUserId);
    }

    public List<PanelUser> getPanelUsers() {
        return em.createQuery("FROM PanelUser").getResultList();
    }

    public boolean createPanelUser(String username, String password) {
        if (findPanelUserByUsername(username) == null) {
            PanelUser newPanelUser = new PanelUser();
            newPanelUser.setUsername(username);
            newPanelUser.setPassword(password);
            newPanelUser.setCreationTime(Timestamp.from(Instant.now()));
            newPanelUser.setIsLocked(false);
            newPanelUser.setPanelUserRankByRankId(em.getReference(PanelUserRank.class, 0));
            em.merge(newPanelUser);
            return true;
        }
        return false;
    }

    public List<PanelUserRank> getRanks() {
        return em.createQuery("FROM PanelUserRank").getResultList();
    }

    public List<PanelUserAuthority> getAuthorities() {
        return em.createQuery("FROM PanelUserAuthority").getResultList();
    }

    public void updatePanelUser(int userId, String username,
                                int rankId, List<Integer> authorityIds) {
        PanelUser panelUser = em.find(PanelUser.class, userId);
        if (panelUser != null) {
            panelUser.setUsername(username);
            panelUser.setPanelUserRankByRankId(em.getReference(PanelUserRank.class, rankId));

            List<PanelUserAuthority> assignedAuthorities = new LinkedList<>();

            for (PanelUserAuthorityAssignment assignedAuth : panelUser.getPanelUserAuthorityAssignmentsById()) {
                PanelUserAuthority authority = assignedAuth.getPanelUserAuthorityByAuthorityId();
                if (!authorityIds.contains(authority.getId())) {
                    deleteAuthAssignmentEntity(assignedAuth);
                } else {
                    assignedAuthorities.add(authority);
                }
            }

            if (!authorityIds.isEmpty()) {
                List<PanelUserAuthority> authoritiesToAssign = em.createQuery("FROM PanelUserAuthority WHERE id IN :authIds")
                        .setParameter("authIds", authorityIds)
                        .getResultList();
                for (PanelUserAuthority authority : authoritiesToAssign) {
                    if (!assignedAuthorities.contains(authority)) {
                        createAuthAssignmentEntity(panelUser, authority);
                    }
                }
            }

            panelUserService.logUserOut(panelUser);
        }
    }

    private void deleteAuthAssignmentEntity(PanelUserAuthorityAssignment assignment) {
        em.remove(assignment);
    }

    private void createAuthAssignmentEntity(PanelUser panelUser, PanelUserAuthority authority) {
        PanelUserAuthorityAssignment assignment = new PanelUserAuthorityAssignment();
        assignment.setPanelUserByPanelUserId(panelUser);
        assignment.setPanelUserAuthorityByAuthorityId(authority);
        em.merge(assignment);
    }

    public boolean changePassword(PanelUser panelUser, String newPassword) {
        return em.createQuery("UPDATE PanelUser SET password = :newPass WHERE id = :id")
                .setParameter("newPass", newPassword)
                .setParameter("id", panelUser.getId())
                .executeUpdate() > 0;
    }
}
