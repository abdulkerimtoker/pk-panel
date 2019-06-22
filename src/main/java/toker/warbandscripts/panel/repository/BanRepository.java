package toker.warbandscripts.panel.repository;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Repository;
import toker.warbandscripts.panel.authentication.PanelUserDetails;
import toker.warbandscripts.panel.entity.Ban;
import toker.warbandscripts.panel.entity.PanelUser;
import toker.warbandscripts.panel.entity.Player;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import java.sql.Timestamp;
import java.time.Instant;
import java.util.Date;
import java.util.List;

@Repository
@Transactional
public class BanRepository {

    @PersistenceContext
    private EntityManager em;

    public List<Ban> getBansByUniqueId(int uniqueId) {
        return em.createQuery("SELECT b FROM Ban b WHERE playerUniqueId = :uniqueId")
                .setParameter("uniqueId", uniqueId)
                .getResultList();
    }

    public List<Ban> getActiveBansByUniqueId(int uniqueId) {
        return em.createQuery("FROM Ban WHERE playerUniqueId = :uniqueId AND undone = false AND expiryTime > current_timestamp")
                .setParameter("uniqueId", uniqueId)
                .getResultList();
    }

    public List<Ban> getPastBansByUniqueId(int uniqueId) {
        return em.createQuery("FROM Ban WHERE playerUniqueId = :uniqueId AND (undone = true OR expiryTime <= current_timestamp)")
                .setParameter("uniqueId", uniqueId)
                .getResultList();
    }

    public void createBan(Player player, PanelUser panelUser, Date expiry, String reason) {
        Ban ban = new Ban();
        ban.setPlayerUniqueId(player.getUniqueId());
        ban.setPanelUserByAdminId(em.getReference(PanelUser.class, panelUser.getId()));
        ban.setTime(Timestamp.from(Instant.now()));
        ban.setExpiryTime(new Timestamp(expiry.getTime()));
        ban.setUndone(false);
        ban.setReason(reason);
        em.merge(ban);
    }

    public boolean unbanSelf(int banId) {
        Ban ban = em.find(Ban.class, banId);
        if (ban != null) {
            PanelUser currentUser = ((PanelUserDetails)SecurityContextHolder.getContext()
                    .getAuthentication().getPrincipal()).getPanelUser();
            if (ban.getPanelUserByAdminId().equals(currentUser)) {
                undoBanEntity(ban);
                return true;
            }
        }
        return false;
    }

    public boolean unban(int banId) {
        Ban ban = em.find(Ban.class, banId);
        if (ban != null) {
            undoBanEntity(ban);
            return true;
        }
        return false;
    }

    private void undoBanEntity(Ban ban) {
        ban.setUndone(true);
    }

    public boolean deleteBan(int banId) {
        Ban ban = em.find(Ban.class, banId);
        if (ban != null) {
            deleteBanEntity(ban);
            return true;
        }
        return false;
    }

    public boolean deleteSelfBan(int banId) {
        Ban ban = em.find(Ban.class, banId);
        if (ban != null) {
            PanelUser currentUser = ((PanelUserDetails)SecurityContextHolder.getContext()
                    .getAuthentication().getPrincipal()).getPanelUser();
            if (ban.getPanelUserByAdminId().equals(currentUser)) {
                deleteBanEntity(ban);
                return true;
            }
        }
        return false;
    }

    private void deleteBanEntity(Ban ban) {
        em.remove(ban);
    }

    public List<Ban> getActiveBans() {
        return em.createQuery("FROM Ban WHERE undone = false AND expiryTime > current_timestamp").getResultList();
    }
}
