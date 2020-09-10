package toker.warbandscripts.panel.util;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Retention(RetentionPolicy.RUNTIME)
public @interface DefaultItemValue {
    int itemId();
}
