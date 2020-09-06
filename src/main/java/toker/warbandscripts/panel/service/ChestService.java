package toker.warbandscripts.panel.service;

import org.springframework.stereotype.Service;
import toker.warbandscripts.panel.entity.Chest;
import toker.warbandscripts.panel.entity.Item;
import toker.warbandscripts.panel.entity.pk.ChestPK;
import toker.warbandscripts.panel.repository.ChestRepository;
import toker.warbandscripts.panel.repository.ChestSlotRepository;
import toker.warbandscripts.panel.repository.ItemRepository;

import javax.persistence.criteria.JoinType;
import javax.transaction.Transactional;
import java.util.Optional;

@Service
@Transactional
public class ChestService {

    private ChestRepository chestRepository;
    private ChestSlotRepository chestSlotRepository;
    private ItemRepository itemRepository;

    public ChestService(ChestRepository chestRepository,
                        ChestSlotRepository chestSlotRepository,
                        ItemRepository itemRepository) {
        this.chestRepository = chestRepository;
        this.chestSlotRepository = chestSlotRepository;
        this.itemRepository = itemRepository;
    }

    public Optional<Chest> getChest(int index, int serverId) {
        return chestRepository.findOne(((root, query, builder) -> {
            root.fetch("slots", JoinType.LEFT);
            return builder.and(
                    builder.equal(root.get("index"), index),
                    builder.equal(root.get("server").get("id"), serverId)
            );
        }));
    }

    public void updateChestSlot(int index, int serverId,
                                int slot, int itemId, int ammo) {
        chestSlotRepository.updateChestSlot(chestRepository.getOne(new ChestPK(index, serverId)),
                slot, itemRepository.getOne(itemId), ammo);
    }
}
