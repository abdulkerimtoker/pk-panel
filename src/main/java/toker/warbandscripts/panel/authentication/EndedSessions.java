package toker.warbandscripts.panel.authentication;

import toker.warbandscripts.panel.entity.PanelUserSession;

import java.util.Collection;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.ConcurrentSkipListSet;

public class EndedSessions {

    private static ConcurrentSkipListSet<Integer> sessions = new ConcurrentSkipListSet<>();

    public static boolean isSessionEnded(int sessionId) {
        return sessions.contains(sessionId);
    }

    public static void endSession(int sessionId) {
        sessions.add(sessionId);
    }

    public static void endSessions(Collection<Integer> sessionIds) {
        sessions.addAll(sessionIds);
    }
}
