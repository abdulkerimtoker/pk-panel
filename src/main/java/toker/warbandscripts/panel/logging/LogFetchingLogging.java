package toker.warbandscripts.panel.logging;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import toker.warbandscripts.panel.authentication.JWTOpenIDAuthenticationToken;
import toker.warbandscripts.panel.entity.Log;
import toker.warbandscripts.panel.entity.PanelUser;
import toker.warbandscripts.panel.entity.PanelUser_;
import toker.warbandscripts.panel.repository.LogRepository;
import toker.warbandscripts.panel.repository.PanelUserRepository;

import java.util.Arrays;

@Aspect
@Component
public class LogFetchingLogging {

    private PanelUserRepository userRepo;
    private LogRepository logRepo;

    public LogFetchingLogging(PanelUserRepository userRepo, LogRepository logRepo) {
        this.userRepo = userRepo;
        this.logRepo = logRepo;
    }

    @Pointcut("execution(* toker.warbandscripts.panel.controller.rest.LogController.searchLogFile(..))")
    public void logFetching() { }

    @Before("logFetching() && args(fileName, words)")
    public void beforeLogFetching(String fileName, String[] words) {
        JWTOpenIDAuthenticationToken auth =
                (JWTOpenIDAuthenticationToken) SecurityContextHolder.getContext()
                        .getAuthentication();

        PanelUser user = userRepo.findOne((root, query, builder) ->
                builder.equal(root.get(PanelUser_.claimedIdentity), auth.getDetails()))
                .orElseThrow();

        ObjectMapper mapper = new ObjectMapper();
        ObjectNode node = mapper.createObjectNode();
        node.put("logFile", fileName);
        ArrayNode wordsNode = mapper.createArrayNode();
        Arrays.stream(words).filter(word -> !word.isBlank()).forEach(wordsNode::add);
        node.set("words", wordsNode);

        Log panelLog = new Log();
        panelLog.setType(Log.Type.LOG_FETCHING);
        panelLog.setUser(user);
        panelLog.setData(node.toString());

        logRepo.save(panelLog);
    }
}
