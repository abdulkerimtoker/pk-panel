package toker.warbandscripts.panel.service;

import org.apache.commons.net.ftp.FTPClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.io.*;
import java.nio.file.Files;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Calendar;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.regex.Pattern;

@Service
public class LogService {

    private PanelConfigurationService configurationService;

    private File localLogFolder;

    private SimpleDateFormat dateYearFormat;
    private SimpleDateFormat longDateYearFormat;
    private SimpleDateFormat dateMonthFormat;
    private SimpleDateFormat dateDayFormat;
    private Pattern queryPattern = Pattern.compile("^(?=.*\\w).(?=[\\w\\d _{}\\[\\]-]).{2,}$");

    private static final String remoteLogFolder = "/logs";

    @Autowired
    public LogService(PanelConfigurationService configurationService) {
        //this.localLogFolder  = new File("/home/pax/mb_warband_dedicated_1174/logs");
        this.configurationService = configurationService;
        this.localLogFolder  = new File(configurationService.getProperty("SERVER_LOGS_DIR"));
        if (!this.localLogFolder.exists()) {
            this.localLogFolder.mkdir();
        }
        this.dateYearFormat     = new SimpleDateFormat("yy");
        this.longDateYearFormat = new SimpleDateFormat("yyyy");
        this.dateMonthFormat    = new SimpleDateFormat("MM");
        this.dateDayFormat      = new SimpleDateFormat("dd");
    }

    public File getLogFile(Date date) {
        String fileName = String.format("server_log_%s_%s_%s.txt",
                dateMonthFormat.format(date),
                dateDayFormat.format(date),
                dateYearFormat.format(date));

        File localLogFile = new File(localLogFolder, fileName);
        File archivedLogFile = new File(new File(System.getProperty("user.dir"), "logs"), fileName);

        if (localLogFile.exists()) {
            return localLogFile;
        } else if (archivedLogFile.exists()) {
            return archivedLogFile;
        }

        return null;
    }

    public File filterLogFile(File logFile, List<String> queries) throws IOException {
        if (!logFile.exists()) {
            return null;
        }

        List<String> filteredQueries = new LinkedList<>();

        for (String query : queries) {
            if (queryPattern.matcher(query).matches()) {
                filteredQueries.add(query);
            }
        }

        File file = new File(localLogFolder, System.currentTimeMillis() + "_log.txt");
        BufferedReader reader = new BufferedReader(new FileReader(logFile));
        BufferedWriter writer = new BufferedWriter(new FileWriter(file));

        String line;
        while ((line = reader.readLine()) != null) {
            for (String query : filteredQueries) {
                if (line.toLowerCase().contains(query.toLowerCase())) {
                    writer.write(line);
                    writer.write("\r\n");
                    break;
                }
            }
        }

        writer.flush();
        writer.close();
        reader.close();

        return file;
    }

    public File getPanelLog(Date date) {
        String fileName = String.format("%s-%s-%s.log",
                longDateYearFormat.format(date),
                dateMonthFormat.format(date),
                dateDayFormat.format(date));

        File logsFolder = new File(System.getProperty("user.dir"), "logs");

        if (logsFolder.exists() && logsFolder.isDirectory()) {
            File logFile = new File(logsFolder, fileName);
            if (logFile.exists() && logFile.isFile()) {
                return logFile;
            }
        }

        return null;
    }

    @Scheduled(fixedDelay = 1000 * 10)
    public void archivePastLogFiles() throws IOException {
        Date today = new Date();
        String fileName = String.format("server_log_%s_%s_%s.txt",
                dateMonthFormat.format(today),
                dateDayFormat.format(today),
                dateYearFormat.format(today));


        if (localLogFolder != null && localLogFolder.exists()) {
            for (File logFile : localLogFolder.listFiles()) {
                if (logFile.isFile() && !logFile.getName().equals(fileName)) {
                    Files.move(logFile.toPath(), new File(new File(System.getProperty("user.dir"), "logs"), logFile.getName()).toPath());
                }
            }
        }
    }
}
