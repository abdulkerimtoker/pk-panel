package toker.warbandscripts.panel.controller.gameapi;

import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import toker.warbandscripts.panel.entity.Chest;
import toker.warbandscripts.panel.entity.ChestSlot;
import toker.warbandscripts.panel.entity.Server;
import toker.warbandscripts.panel.entity.pk.ChestPK;
import toker.warbandscripts.panel.repository.ChestRepository;
import toker.warbandscripts.panel.service.ChestService;

import java.util.Collection;
import java.util.Comparator;

@RestController
public class ChestGameController {

    private ChestService chestService;

    public ChestGameController(ChestService chestService) {
        this.chestService = chestService;
    }

    @GetMapping("/gameapi/chestget")
    public String chest(int id, int instanceid)
            throws ChangeSetPersister.NotFoundException {
        Server server = (Server) SecurityContextHolder.getContext()
                .getAuthentication().getPrincipal();
        Chest chest = chestService.getChest(id, server.getId())
                .orElseThrow(ChangeSetPersister.NotFoundException::new);
        Collection<ChestSlot> slots = chest.getSlots();
        StringBuilder sb =
                new StringBuilder(String.format("%d|%d|%d", id, instanceid, slots.size()));

        slots.stream().sorted(Comparator.comparingInt(ChestSlot::getSlot)).forEach(chestSlot -> {
            sb.append('|');
            sb.append(chestSlot.getItem().getId());
        });

        slots.stream().sorted(Comparator.comparingInt(ChestSlot::getSlot)).forEach(chestSlot -> {
            sb.append('|');
            sb.append(chestSlot.getAmmo());
        });

        return sb.toString();
    }

    @GetMapping("/gameapi/chestput")
    public void put(int id, int slot, int item, int ammo) {
        Server server = (Server) SecurityContextHolder.getContext()
                .getAuthentication().getPrincipal();
        chestService.updateChestSlot(id, server.getId(), slot + 1, item, ammo);
    }
}
