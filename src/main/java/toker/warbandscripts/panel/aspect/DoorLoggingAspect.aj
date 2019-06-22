package toker.warbandscripts.panel.aspect;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import toker.warbandscripts.panel.entity.Door;
import toker.warbandscripts.panel.entity.DoorKey;
import toker.warbandscripts.panel.entity.Player;
import toker.warbandscripts.panel.repository.*;

public aspect DoorLoggingAspect {

    private Logger logger;

    @Autowired
    public void setLogger(Logger logger) {
        this.logger = logger;
    }

    pointcut addingDoorKey(Door door, Player player, boolean isOwner)
            : execution(void DoorRepository.createDoorKeyEntity(Door, Player, boolean)) && args(door, player, isOwner);

    void around(Door door, Player player, boolean isOwner) : addingDoorKey(door, player, isOwner) {
        String admin = SecurityContextHolder.getContext().getAuthentication().getName();
        System.out.println("hello");
        proceed(door, player, isOwner);

        if (isOwner) {
            logger.info(String.format("%s gave %s a key on %s as an owner",
                    admin, player.getName(), door.getName()));
        } else {
            logger.info(String.format("%s gave %s a key on %s as a user",
                    admin, player.getName(), door.getName()));
        }
    }

    pointcut removingDoorKey(DoorKey doorKey) : execution(void DoorRepository.deleteDoorKeyEntity(DoorKey)) && args(doorKey);

    void around(DoorKey doorKey) : removingDoorKey(doorKey) {
        String admin = SecurityContextHolder.getContext().getAuthentication().getName();
        System.out.println("hello");
        proceed(doorKey);
        logger.info(String.format("%s removed %s's key on %s", admin, doorKey.getPlayerByUserId().getName(), doorKey.getDoorByDoorId().getName()));
    }
}
