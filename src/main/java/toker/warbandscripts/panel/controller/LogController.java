package toker.warbandscripts.panel.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import toker.warbandscripts.panel.service.LogService;

import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

@Controller
@RequestMapping("/log")
public class LogController {

    private LogService logService;
    private SimpleDateFormat dateFormat;

    @Autowired
    public LogController(LogService logService) {
        this.logService = logService;
        this.dateFormat = new SimpleDateFormat("yy-MM-dd");
    }

    @Secured("ROLE_LOG_FETCHER")
    @RequestMapping("layout")
    public String layout() {
        return "log/layout";
    }

    @Secured("ROLE_LOG_FETCHER")
    @PostMapping("refresh")
    public String download(String date) throws Exception {
        //logService.downloadLogFile(dateFormat.parse(date));
        return "log/layout";
    }

    @Secured("ROLE_LOG_FETCHER")
    @PostMapping("fetch")
    @ResponseBody
    public HttpEntity<FileSystemResource> fetch(HttpServletResponse response,
                            @RequestParam(required = false) String date,
                            @RequestParam List<String> queries) {
        try {
            Date logDate;
            try {
                 logDate = dateFormat.parse(date);
            } catch (Exception e) {
                logDate = new Date();
            }

            File logFile = logService.filterLogFile(logService.getLogFile(logDate), queries);

            if (logFile != null) {
                HttpHeaders headers = new HttpHeaders();
                headers.setContentType(MediaType.TEXT_PLAIN);
                headers.set(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=" + logFile.getName());
                headers.setContentLength(logFile.length());

                return new HttpEntity<>(new FileSystemResource(logFile), headers);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return null;
    }

    @Secured("ROLE_PANEL_LOG_FETCHER")
    @PostMapping("fetchpanellog")
    @ResponseBody
    public HttpEntity<FileSystemResource> fetchPanelLog(@RequestParam(required = false) String date) {
        try {
            Date logDate;
            try {
                logDate = dateFormat.parse(date);
            } catch (Exception e) {
                logDate = new Date();
            }

            File logFile = logService.getPanelLog(logDate);

            if (logFile != null) {
                HttpHeaders headers = new HttpHeaders();
                headers.setContentType(MediaType.TEXT_PLAIN);
                headers.set(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=" + logFile.getName());
                headers.setContentLength(logFile.length());

                return new HttpEntity<>(new FileSystemResource(logFile), headers);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return null;
    }
}
