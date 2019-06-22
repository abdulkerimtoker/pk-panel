package toker.warbandscripts.panel.repository;

import org.springframework.stereotype.Repository;
import toker.warbandscripts.panel.entity.Door;
import toker.warbandscripts.panel.entity.DoorKey;
import toker.warbandscripts.panel.entity.Player;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import java.util.List;

@Repository
@Transactional
public class DoorRepository {

    @PersistenceContext
    private EntityManager em;

    public List<Door> getDoors() {
        return em.createQuery("SELECT d FROM Door d").getResultList();
    }

    public Door getDoor(int doorId) {
        return em.find(Door.class, doorId);
    }

    public DoorKey getDoorKeyById(int doorKeyId) {
        return em.find(DoorKey.class, doorKeyId);
    }

    public void removeDoorKey(int doorKeyId) {
        deleteDoorKeyEntity(getDoorKeyById(doorKeyId));
    }

    private void deleteDoorKeyEntity(DoorKey doorKey) {
        em.remove(doorKey);
    }

    public boolean addDoorKey(int playerId, int doorId, boolean isOwner) {
        Player player = em.find(Player.class, playerId);
        Door door = em.find(Door.class, doorId);

        if (player != null && door != null) {
            createDoorKeyEntity(door, player, isOwner);
            return true;
        }

        return false;
    }

    private void createDoorKeyEntity(Door door, Player player, boolean isOwner) {
        DoorKey newDoorKey = new DoorKey();
        newDoorKey.setPlayerByUserId(player);
        newDoorKey.setDoorByDoorId(door);
        newDoorKey.setIsOwner(isOwner);
        em.merge(newDoorKey);
    }

    public boolean updateDoor(Door door) {
        Door found = em.find(Door.class, door.getId());
        if (found != null) {
            found.setName(door.getName());
            found.setLocked(door.getLocked());
            return true;
        }
        return false;
    }
}
