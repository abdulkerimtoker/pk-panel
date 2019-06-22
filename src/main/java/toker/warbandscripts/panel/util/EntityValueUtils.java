package toker.warbandscripts.panel.util;

import org.hibernate.collection.internal.PersistentBag;

import javax.persistence.Entity;
import java.lang.reflect.Method;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;

public class EntityValueUtils {

    public static List<EntityValueChange> getValueChangesForEntity(Object newValue, Object oldValue) {
        List<EntityValueChange> changes = new LinkedList<>();

        try {
            if (oldValue.getClass().getName().equals(newValue.getClass().getName())) {
                Class type = oldValue.getClass();
                for (Method method : type.getDeclaredMethods()) {
                    if (method.getName().startsWith("get") && method.getName().length() > 3) {
                        String fieldName = method.getName().substring(3);

                        if (fieldName.endsWith("sById")) {
                            continue;
                        }

                        Object oldFieldValue = method.invoke(oldValue);
                        Object newFieldValue = method.invoke(newValue);

                        Class fieldType = method.getReturnType();
                        String fieldTypeName = fieldType.getSimpleName();

                        if (fieldName.equals(String.format("%sBy%sId", fieldTypeName, fieldTypeName))) {
                            if (!fieldType.isAnnotationPresent(Entity.class)) {
                                continue;
                            }

                            Method getIdMethod = fieldType.getDeclaredMethod("getId");

                            if (oldFieldValue != null) {
                                oldFieldValue = getIdMethod.invoke(oldFieldValue);
                            }
                            if (newFieldValue != null) {
                                newFieldValue = getIdMethod.invoke(newFieldValue);
                            }
                        }

                        if (oldFieldValue == null) {
                            oldFieldValue = "null";
                        }

                        if (newFieldValue != null && !oldFieldValue.equals(newFieldValue)) {
                            changes.add(new EntityValueChange(type, fieldName, oldFieldValue, newFieldValue));
                        }
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return changes;
    }
}
