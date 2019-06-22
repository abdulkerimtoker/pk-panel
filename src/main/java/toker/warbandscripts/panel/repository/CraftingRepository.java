package toker.warbandscripts.panel.repository;

import org.springframework.stereotype.Repository;
import toker.warbandscripts.panel.entity.*;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.JoinType;
import javax.persistence.criteria.Root;
import javax.transaction.Transactional;
import java.util.List;

@Repository
public class CraftingRepository {

    @PersistenceContext
    private EntityManager em;

    @Transactional
    public CraftingStation getCraftingStation(int id, boolean fetchInstances) {
        if (fetchInstances) {
            return em.createQuery("SELECT cs FROM CraftingStation cs LEFT JOIN FETCH cs.craftingStationInstances WHERE cs.id = :id", CraftingStation.class)
                    .setParameter("id", id)
                    .getSingleResult();
        } else {
            return em.find(CraftingStation.class, id);
        }
    }

    public CraftingStation getCraftingStation(int id) {
        return getCraftingStation(id, false);
    }

    @Transactional
    public List<CraftingStation> getCraftingStations() {
        return em.createQuery("FROM CraftingStation").getResultList();
    }

    @Transactional
    public CraftingStation createCraftingStation(CraftingStation craftingStation) {
        return em.merge(craftingStation);
    }

    @Transactional
    public boolean removeCraftingStation(int id) {
        return em.createQuery("DELETE FROM CraftingStation WHERE id = :id")
                .setParameter("id", id)
                .executeUpdate() > 0;
    }

    @Transactional
    public boolean updateCraftingStation(CraftingStation craftingStation) {
        CraftingStation current = em.find(CraftingStation.class, craftingStation.getId());
        if (current != null) {
            current.setName(craftingStation.getName());
            return true;
        }
        return false;
    }

    @Transactional
    public CraftingRecipe getCraftingRecipe(int id) {
        return em.find(CraftingRecipe.class, id);
    }

    @Transactional
    public CraftingRecipe createCraftingRecipe(CraftingRecipe craftingRecipe) {
        return em.merge(craftingRecipe);
    }

    @Transactional
    public boolean removeCraftingRecipe(int id) {
        em.createQuery("DELETE FROM CraftingRequest WHERE craftingRecipe.id = :id")
                .setParameter("id", id)
                .executeUpdate();
        return em.createQuery("DELETE FROM CraftingRecipe WHERE id = :id")
                .setParameter("id", id)
                .executeUpdate() > 0;
    }

    @Transactional
    public boolean updateCraftingRecipe(CraftingRecipe craftingRecipe) {
        CraftingRecipe current = em.find(CraftingRecipe.class, craftingRecipe.getId());
        if (current != null) {
            current.setProfessionByProfessionId(em.getReference(Profession.class, craftingRecipe.getProfessionByProfessionId().getId()));
            current.setProfessionTier(craftingRecipe.getProfessionTier());
            current.setItemByItemId(em.getReference(Item.class, craftingRecipe.getItemByItemId().getId()));
            current.setPrice(craftingRecipe.getPrice());
            current.setHours(craftingRecipe.getHours());
            return true;
        }
        return false;
    }

    @Transactional
    public CraftingRecipeItemRequirement createCraftingRecipeItemRequirement(CraftingRecipeItemRequirement itemRequirement) {
        return em.merge(itemRequirement);
    }

    @Transactional
    public boolean removeCraftingRecipeItemRequirement(int id) {
        return em.createQuery("DELETE FROM CraftingRecipeItemRequirement WHERE id = :id")
                .setParameter("id", id)
                .executeUpdate() > 0;
    }

    @Transactional
    public boolean updateCraftingRecipeItemRequirement(CraftingRecipeItemRequirement itemRequirement) {
        CraftingRecipeItemRequirement current = em.find(CraftingRecipeItemRequirement.class, itemRequirement.getId());
        if (current != null) {
            current.setItemByItemId(em.getReference(Item.class, itemRequirement.getItemByItemId().getId()));
            current.setAmount(itemRequirement.getAmount());
            return true;
        }
        return false;
    }

    @Transactional
    public CraftingStationInstance createCraftingStationInstance(CraftingStationInstance stationInstance) {
        return em.merge(stationInstance);
    }
}
