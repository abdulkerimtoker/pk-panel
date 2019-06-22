package toker.warbandscripts.panel.util;

import java.io.BufferedReader;
import java.nio.CharBuffer;

public class StringUtils {

    public static boolean contains(char[] source, int sourceOffset, int sourceIndex,
                                   char[] target, int targetOffset, int targetIndex) {
        if (sourceIndex < 0 || targetIndex < 0) {
            return false;
        }
        if (sourceIndex >= source.length) {
            sourceIndex = source.length - 1;
        }
        if (targetIndex >= target.length) {
            targetIndex = target.length - 1;
        }

        for (int i = sourceOffset; i < sourceIndex; i++) {
            if ((targetIndex + i) > sourceIndex) {
                return false;
            }
            boolean contains = true;
            for (int j = targetOffset; j < targetIndex; j++) {
                if (source[i + j] != target[j]) {
                    contains = false;
                    break;
                }
            }
            if (contains) {
                return true;
            }
        }
        return false;
    }
}
