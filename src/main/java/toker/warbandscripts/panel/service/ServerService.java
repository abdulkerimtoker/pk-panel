package toker.warbandscripts.panel.service;

import org.springframework.stereotype.Service;
import toker.warbandscripts.panel.entity.Server;
import toker.warbandscripts.panel.repository.ServerRepository;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

@Service("serverService")
public class ServerService {

    private ServerRepository serverRepository;

    private Map<Class<?>, Method> typeToGetterMap = new HashMap<>();

    public ServerService(ServerRepository serverRepository) {
        this.serverRepository = serverRepository;
    }

    public String getServerRoleName(String role, Object entity) {
        Server server = getServerForEntity(entity);
        if (server == null) {
            return "ROLE_INVALID";
        }
        return String.format("ROLE_%d_%s", server.getId(), role);
    }

    private Server getServerForEntity(Object entity) {
        Class _class = entity.getClass();

        if (!typeToGetterMap.containsKey(_class)) {
            for (Field field : _class.getDeclaredFields()) {
                if (field.getType().equals(Server.class)) {
                    String getterName = "get" + Character.toUpperCase(field.getName().charAt(0))
                            + field.getName().substring(1);
                    try {
                        Method getter = _class.getDeclaredMethod(getterName);
                        typeToGetterMap.put(_class, getter);
                    } catch (NoSuchMethodException e) {
                        e.printStackTrace();
                    }
                    break;
                }
            }
        }

        try {
            Server server = (Server)typeToGetterMap.get(_class).invoke(entity);
            return serverRepository.findByKey(server.getKey());
        } catch (IllegalAccessException | InvocationTargetException e) {
            e.printStackTrace();
        }

        return null;
    }
}
