package toker.warbandscripts.panel.aspect;

import org.apache.log4j.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;

import toker.warbandscripts.panel.entity.Inventory;
import toker.warbandscripts.panel.entity.InventorySlot;
import toker.warbandscripts.panel.entity.Player;
import toker.warbandscripts.panel.util.EntityValueChange;
import toker.warbandscripts.panel.util.EntityValueUtils;

import java.util.Collection;
import java.util.List;

public aspect PlayerLoggingAspect {

    private Logger logger;

    @Autowired
    public void setLogger(Logger logger) {
        this.logger = logger;
    }

    pointcut playerUpdating(Player from, Player to)
            : execution(void PlayerRepository.updatePlayerEntity(Player, Player)) && args(from, to);

    void around(Player from, Player to) : playerUpdating(from, to) {
        String admin = SecurityContextHolder.getContext().getAuthentication().getName();

        List<EntityValueChange> changes = EntityValueUtils.getValueChangesForEntity(from, to);

        proceed(from, to);

        for (EntityValueChange change : changes) {
            logger.info(String.format("%s changed %s's %s from %s to %s",
                    admin, to.getId().toString(),
                    change.fieldName, change.oldValue.toString(), change.newValue.toString()));
        }
    }

    pointcut inventoryUpdating(Inventory inventory, List<InventorySlot> newSlotValues) :
            execution(void PlayerRepository.updatePlayerInventorySlotEntities(Inventory, List<InventorySlot>)) &&
            args(inventory, newSlotValues);

    void around(Inventory inventory, List<InventorySlot> newSlotValues)
            : inventoryUpdating(inventory, newSlotValues) {
        String admin = SecurityContextHolder.getContext().getAuthentication().getName();

        Collection<InventorySlot> slots = inventory.getInventorySlotsById();

        for (InventorySlot slot : slots) {
            for (InventorySlot newSlotValue : newSlotValues) {
                if (slot.getSlot() == newSlotValue.getSlot()) {
                    if (!slot.getItemByItemId().getId().equals(newSlotValue.getItemByItemId().getId())) {
                        logger.info(String.format("%s changed %s's item at inventory slot %d from %d to %d",
                                admin, inventory.getPlayerByPlayerId().getName(),
                                slot.getSlot(), slot.getItemByItemId().getId(), newSlotValue.getItemByItemId().getId()));
                    }
                    if (!slot.getAmmo().equals(newSlotValue.getAmmo())) {
                        logger.info(String.format("%s changed %s's ammo at inventory slot %d from %d to %d",
                                admin, inventory.getPlayerByPlayerId().getName(),
                                slot.getSlot(), slot.getAmmo(), newSlotValue.getAmmo()));
                    }
                }
            }
        }

        proceed(inventory, newSlotValues);
    }
}
