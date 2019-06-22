package toker.warbandscripts.panel.repository;

import org.springframework.stereotype.Repository;
import toker.warbandscripts.panel.entity.ServerConfiguration;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import java.util.List;

@Repository
public class ServerRepository {

    @PersistenceContext
    private EntityManager em;

    @Transactional
    public List<ServerConfiguration> getServerConfigurations() {
        return em.createQuery("FROM ServerConfiguration", ServerConfiguration.class)
                .getResultList();
    }

    @Transactional
    public boolean updateServerConfiguration(ServerConfiguration serverConfiguration) {
        ServerConfiguration current = em.find(ServerConfiguration.class, serverConfiguration.getId());
        if (current != null) {
            current.setTextValue(serverConfiguration.getTextValue());
            current.setIntValue(serverConfiguration.getIntValue());
            current.setFloatValue(serverConfiguration.getFloatValue());
            return true;
        }
        return false;
    }
}
