package toker.warbandscripts.panel.repository;

import org.springframework.stereotype.Repository;
import toker.warbandscripts.panel.entity.Item;
import toker.warbandscripts.panel.entity.ItemType;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
@Transactional
public class ItemRepository {

    @PersistenceContext
    private EntityManager em;

    private List<Item> items;
    private Map<Integer, List<Item>> itemsByType;

    public ItemRepository() {
        itemsByType = new HashMap<>();
    }

    public List<Item> getItems() {
        if (items == null) {
            items = em.createQuery("FROM Item").getResultList();
            items.size();
        }
        return items;
    }

    public List<Item> getItemsByType(int typeId) {
        if (itemsByType.containsKey(typeId)) {
            return itemsByType.get(typeId);
        } else {
            itemsByType.put(typeId, em.createQuery("SELECT i FROM Item i WHERE i.itemTypeByType.id = :id")
                    .setParameter("id", typeId)
                    .getResultList());
            return itemsByType.get(typeId);
        }
    }

    public List<ItemType> getItemTypes() {
        return em.createQuery("SELECT it FROM ItemType it").getResultList();
    }
}
