package toker.warbandscripts.panel.repository;

import org.springframework.stereotype.Repository;
import toker.warbandscripts.panel.entity.AdminPermissions;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import java.util.List;

@Repository
public class IGPermissionsRepository {

    @PersistenceContext
    private EntityManager em;

    @Transactional
    public List<AdminPermissions> getPermissions() {
        return em.createQuery("FROM AdminPermissions").getResultList();
    }

    @Transactional
    public void updatePermissions(List<AdminPermissions> newPermissions) {
        for (AdminPermissions perms : getPermissions()) {
            for (AdminPermissions newPerms : newPermissions) {
                if (perms.getUniqueId().equals(newPerms.getUniqueId())) {
                    updatePermissionsEntity(perms, newPerms);
                    newPermissions.remove(newPerms);
                    break;
                }
            }
        }
    }

    private void updatePermissionsEntity(AdminPermissions entity, AdminPermissions values) {
        entity.setAdminItems(values.getAdminItems());
        entity.setAllItems(values.getAllItems());
        entity.setAnimals(values.getAnimals());
        entity.setAnnounce(values.getAnnounce());
        entity.setFactions(values.getFactions());
        entity.setFreeze(values.getFreeze());
        entity.setGodlikeTroop(values.getGodlikeTroop());
        entity.setGold(values.getGold());
        entity.setHealSelf(values.getHealSelf());
        entity.setJoinFactions(values.getJoinFactions());
        entity.setKick(values.getKick());
        entity.setKillFade(values.getKillFade());
        entity.setMute(values.getMute());
        entity.setOverridePoll(values.getOverridePoll());
        entity.setPanel(values.getPanel());
        entity.setPermanentBan(values.getPermanentBan());
        entity.setShips(values.getShips());
        entity.setTeleportSelf(values.getTeleportSelf());
        entity.setTemporaryBan(values.getTemporaryBan());
    }

    @Transactional
    public boolean addPermissions(int uniqueId) {
        if (em.find(AdminPermissions.class, uniqueId) == null) {
            AdminPermissions permissions = new AdminPermissions();
            permissions.setUniqueId(uniqueId);
            em.merge(permissions);
            return true;
        }
        return false;
    }

    @Transactional
    public boolean deletePermissions(int uniqueId) {
        return em.createQuery("DELETE FROM AdminPermissions WHERE uniqueId = :uniqueId")
                .setParameter("uniqueId", uniqueId)
                .executeUpdate() > 0;
    }
}
