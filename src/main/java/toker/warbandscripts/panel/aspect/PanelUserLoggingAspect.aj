package toker.warbandscripts.panel.aspect;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import toker.warbandscripts.panel.entity.PanelUser;
import toker.warbandscripts.panel.entity.PanelUserAuthority;
import toker.warbandscripts.panel.entity.PanelUserAuthorityAssignment;
import toker.warbandscripts.panel.repository.*;

public aspect PanelUserLoggingAspect {

    private Logger logger;

    @Autowired
    public void setLogger(Logger logger) {
        this.logger = logger;
    }

    pointcut assigningAuthority(PanelUser panelUser, PanelUserAuthority authority)
            : execution(void PanelUserRepository.createAuthAssignmentEntity(PanelUser, PanelUserAuthority)) && args(panelUser, authority);

    void around(PanelUser panelUser, PanelUserAuthority authority) : assigningAuthority(panelUser, authority) {
        String admin = SecurityContextHolder.getContext().getAuthentication().getName();

        proceed(panelUser, authority);

        logger.info(String.format("%s assigned %s to %s", admin, authority.getAuthorityName(), panelUser.getUsername()));
    }

    pointcut revokingAuthority(PanelUserAuthorityAssignment assignment)
            : execution(void PanelUserRepository.deleteAuthAssignmentEntity(PanelUserAuthorityAssignment)) && args(assignment);

    void around(PanelUserAuthorityAssignment assignment) : revokingAuthority(assignment) {
        String admin = SecurityContextHolder.getContext().getAuthentication().getName();

        proceed(assignment);

        logger.info(String.format("%s revoked %s from %s", admin,
                assignment.getPanelUserAuthorityByAuthorityId().getAuthorityName(),
                assignment.getPanelUserByPanelUserId().getUsername()));
    }
}
