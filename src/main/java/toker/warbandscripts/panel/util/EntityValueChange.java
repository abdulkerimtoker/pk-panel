package toker.warbandscripts.panel.util;

public class EntityValueChange {
    public Class type;
    public String fieldName;
    public Object oldValue;
    public Object newValue;

    public EntityValueChange(Class type, String fieldName, Object oldValue, Object newValue) {
        this.type = type;
        this.fieldName = fieldName;
        this.oldValue = oldValue;
        this.newValue = newValue;
    }
}
