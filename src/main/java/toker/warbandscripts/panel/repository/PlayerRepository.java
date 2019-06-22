package toker.warbandscripts.panel.repository;

import org.springframework.stereotype.Repository;
import toker.warbandscripts.panel.entity.*;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Selection;
import javax.transaction.Transactional;
import java.util.*;

@Repository
@Transactional
public class PlayerRepository {

    @PersistenceContext
    private EntityManager em;

    public Player getPlayerById(int playerId) {
        return em.find(Player.class, playerId);
    }

    public List<Player> getPlayersBySimilarNameOrId(String search) {
        return em.createQuery("SELECT p FROM Player p WHERE LOWER(p.name) LIKE :search OR CAST(p.uniqueId AS string) LIKE :search OR CAST(p.id AS string) LIKE :search")
            .setParameter("search", '%' + search.toLowerCase() + '%')
            .getResultList();
    }

    public void updatePlayer(Player newState) {
        Player player = em.find(Player.class, newState.getId());
        if (player != null) {
            updatePlayerEntity(newState, player);
        }
    }

    private void updatePlayerEntity(Player newState, Player oldState) {
        oldState.setUniqueId(newState.getUniqueId());
        oldState.setName(newState.getName());
        oldState.setGold(newState.getGold());
        oldState.setFactionByFactionId(em.getReference(Faction.class, newState.getFactionByFactionId().getId()));
        oldState.setTroopByTroopId(em.getReference(Troop.class, newState.getTroopByTroopId().getId()));
        oldState.setHeadArmor(newState.getHeadArmor());
        oldState.setFootArmor(newState.getFootArmor());
        oldState.setBodyArmor(newState.getBodyArmor());
        oldState.setGlovesArmor(newState.getGlovesArmor());
        oldState.setItem0(newState.getItem0());
        oldState.setItem1(newState.getItem1());
        oldState.setItem2(newState.getItem2());
        oldState.setItem3(newState.getItem3());
        oldState.setHorse1(newState.getHorse1());
        oldState.setHorse2(newState.getHorse2());
        oldState.setHorse3(newState.getHorse3());
        oldState.setRidingTier(newState.getRidingTier());
    }

    public Inventory getPlayerInventory(int playerId) {
        Object result = em.createQuery("SELECT i FROM Inventory i WHERE i.playerByPlayerId.id = :playerId")
                .setParameter("playerId", playerId)
                .getSingleResult();
        return result != null ? (Inventory)result : null;
    }

    public void updatePlayerInventory(int playerId, List<InventorySlot> newSlotValues) {
        Inventory inventory = getPlayerInventory(playerId);
        updatePlayerInventorySlotEntities(inventory, newSlotValues);
    }

    private void updatePlayerInventorySlotEntities(Inventory inventory, List<InventorySlot> newSlotValues) {
        Collection<InventorySlot> slots = inventory.getInventorySlotsById();

        for (InventorySlot slot : slots) {
            for (InventorySlot newSlotValue : newSlotValues) {
                if (slot.getSlot() == newSlotValue.getSlot()) {
                    if (slot.getItemByItemId().getId() != newSlotValue.getItemByItemId().getId()) {
                        slot.setItemByItemId(em.getReference(Item.class, newSlotValue.getItemByItemId().getId()));
                    }
                    if (slot.getAmmo() != newSlotValue.getAmmo()) {
                        slot.setAmmo(newSlotValue.getAmmo());
                    }
                    continue;
                }
            }
        }
    }

    public List<Troop> getPlayerTroops() {
        return em.createQuery("SELECT t FROM Troop t").getResultList();
    }

    public List<Faction> getPlayerFactions() {
        return em.createQuery("SELECT f FROM Faction f").getResultList();
    }

    public List<Profession> getProfessions() {
        CriteriaQuery query = em.getCriteriaBuilder().createQuery(Profession.class);
        query = query.select(query.from(Profession.class));
        return em.createQuery(query).getResultList();
    }

    public void saveFactions(List<Faction> factions) {
        for (Faction faction : factions) {
            Faction found = em.find(Faction.class, faction.getId());
            if (found != null) {
                found.setName(faction.getName());
                found.setBannerId(faction.getBannerId());
            }
        }
    }

    public boolean assignProfession(int playerId, int professionId, int tier) {
        Player player = em.find(Player.class, playerId);
        if (player != null) {
            em.createQuery("DELETE FROM ProfessionAssignment WHERE playerByPlayerId = :player AND professionByProfessionId.id = :professionId")
                    .setParameter("player", player)
                    .setParameter("professionId", professionId)
                    .executeUpdate();
            Profession profession = em.find(Profession.class, professionId);
            if (profession != null && tier <= profession.getMaxTier() && tier > 0) {
                ProfessionAssignment assignment = new ProfessionAssignment();
                assignment.setPlayerByPlayerId(player);
                assignment.setProfessionByProfessionId(profession);
                assignment.setTier(tier);
                em.merge(assignment);
                return true;
            }
        }
        return false;
    }

    public boolean revokeProfession(int playerId, int professionId) {
        return em.createQuery("DELETE FROM ProfessionAssignment WHERE playerByPlayerId.id = :playerId AND professionByProfessionId.id = :professionId")
                .setParameter("playerId", playerId)
                .setParameter("professionId", professionId)
                .executeUpdate() > 0;
    }

    public Set<Integer> getIPAssociatedGUIDs(int guid) {
        List<String> ipRecords = em.createQuery("SELECT DISTINCT ipr.ipAddress FROM IpRecord ipr WHERE ipr.uniqueId = :guid", String.class)
            .setParameter("guid", guid)
            .getResultList();

        Set<Integer> associatedGUIDs = new LinkedHashSet<>();

        for (String ipAddress : ipRecords) {
            List<Integer> guids = em.createQuery("SELECT DISTINCT ipr.uniqueId FROM IpRecord ipr WHERE ipr.ipAddress = :ipAddress", Integer.class)
                    .setParameter("ipAddress", ipAddress)
                    .getResultList();
            associatedGUIDs.addAll(guids);
        }

        return associatedGUIDs;
    }

    public List<Player> findPlayersWithItem(int itemId) {
        LinkedHashSet<Player> players = new LinkedHashSet<>();

        players.addAll(
                em.createQuery("FROM Player p WHERE p.item0 = :item OR p.item1 = :item OR p.item2 = :item OR p.item3 = :item")
                .setParameter("item", itemId)
                .getResultList());

        players.addAll(
                em.createQuery("SELECT slot.inventoryByInventoryId.playerByPlayerId FROM InventorySlot slot " +
                "INNER JOIN slot.inventoryByInventoryId " +
                "INNER JOIN slot.inventoryByInventoryId.playerByPlayerId " +
                "WHERE slot.itemByItemId.id = :item", Player.class)
                .setParameter("item", itemId)
                .getResultList());

        return new ArrayList<>(players);
    }
}
