package toker.warbandscripts.panel.aspect;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import toker.warbandscripts.panel.entity.Ban;
import toker.warbandscripts.panel.entity.PanelUser;
import toker.warbandscripts.panel.entity.Player;
import toker.warbandscripts.panel.repository.*;

import java.util.Date;

public aspect BanLoggingAspect {

    private Logger logger;

    @Autowired
    public void setLogger(Logger logger) {
        this.logger = logger;
    }

    pointcut banning(Player player, PanelUser panelUser, Date expiry, String reason)
            : execution(void BanRepository.createBan(Player, PanelUser, Date, String)) &&
            args(player, panelUser, expiry, reason);

    void around(Player player, PanelUser panelUser, Date expiry, String reason) : banning(player, panelUser, expiry, reason) {
        proceed(player, panelUser, expiry, reason);
        logger.info(String.format("%s banned %s till %s",
                panelUser.getUsername(), player.getName(), expiry.toGMTString()));
    }

    pointcut unbanning(Ban ban) : execution(void BanRepository.undoBanEntity(Ban)) && args(ban);

    void around(Ban ban) : unbanning(ban) {
        String admin = SecurityContextHolder.getContext().getAuthentication().getName();

        proceed(ban);

        logger.info(String.format("%s unbanned %d (Ban ID: %d)", admin, ban.getPlayerUniqueId(), ban.getId()));
    }

    pointcut deletingBan(Ban ban) : execution(void BanRepository.deleteBanEntity(Ban)) && args(ban);

    void around(Ban ban) : deletingBan(ban) {
        String admin = SecurityContextHolder.getContext().getAuthentication().getName();

        proceed(ban);

        logger.info(String.format("%s deleted %d's ban (Ban ID: %d)", admin, ban.getPlayerUniqueId(), ban.getId()));
    }
}
