package toker.warbandscripts.panel.aspect;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import toker.warbandscripts.panel.controller.LogController;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

public aspect LogsAspect {

    private Logger logger;

    @Autowired
    public void setLogger(Logger logger) {
        this.logger = logger;
    }

    pointcut adminLogFetching(HttpServletResponse response, String date, List<String> queries) :
            execution(* LogController.fetch(HttpServletResponse, String, List<String>)) &&
            args(response, date, queries);

    after(HttpServletResponse response, String date, List<String> queries) : adminLogFetching(response, date, queries) {
        String adminName = SecurityContextHolder.getContext().getAuthentication().getName();
        String keywords = "";
        for (String query: queries) {
            if (!query.isEmpty()) {
                keywords += query + ", ";
            }
        }
        logger.info(String.format("%s fetched logs for keywords: %s", adminName, keywords));
    }
}
