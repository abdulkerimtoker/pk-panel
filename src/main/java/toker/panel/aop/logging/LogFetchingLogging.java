package toker.panel.aop.logging;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.security.core.context.SecurityContextHolder;
import toker.panel.authentication.JWTOpenIDAuthenticationToken;
import toker.panel.entity.PanelUser;
import toker.panel.entity.Log;
import toker.panel.entity.PanelUser_;
import toker.panel.repository.LogRepository;
import toker.panel.repository.PanelUserRepository;

import javax.inject.Inject;
import java.util.Arrays;

@Aspect
public class LogFetchingLogging {

    private PanelUserRepository userRepo;
    private LogRepository logRepo;

    @Inject
    public void setUserRepo(PanelUserRepository userRepo) {
        this.userRepo = userRepo;
    }

    @Inject
    public void setLogRepo(LogRepository logRepo) {
        this.logRepo = logRepo;
    }

    @Pointcut("target(toker.panel.controller.rest.LogController) && " +
            "execution(* searchLogFile(String, String[])) && args(fileName, words)")
    public void logFetching(String fileName, String[] words) {}

    @Before("logFetching(fileName, words)")
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
        node.put("serverId", auth.getSelectedServerId());
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
