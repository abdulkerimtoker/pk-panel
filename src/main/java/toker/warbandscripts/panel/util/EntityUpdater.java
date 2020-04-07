package toker.warbandscripts.panel.util;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class EntityUpdater {

    public static void update(Object source, Object target, String... fieldNames) {
        for (String fieldName : fieldNames) {
            String getterName = "get" +
                    Character.toUpperCase(fieldName.charAt(0)) +
                    fieldName.substring(1);
            String setterName = "set" +
                    Character.toUpperCase(fieldName.charAt(0)) +
                    fieldName.substring(1);
            try {
                Method getter = source.getClass().getDeclaredMethod(getterName);
                Method setter = target.getClass().getDeclaredMethod(setterName);
                Object value = getter.invoke(source);
                setter.invoke(target, value);
            } catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException e) {
                e.printStackTrace();
            }
        }
    }
}
