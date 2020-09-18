package toker.panel.service;

import org.springframework.stereotype.Service;
import toker.panel.entity.Server;

import java.io.*;
import java.util.Arrays;
import java.util.Set;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

@Service
public class LogService {

    private Pattern pattern = Pattern.compile("[\\w _\\-\\[\\]:()]{3,128}");

    public String[] getLogFileNames(Server server) {
        File serverFolder = new File(new File(server.getExePath()).getParent());
        File logFolder = new File(serverFolder, "logs");
        return logFolder.list();
    }

    public File getLogFile(Server server, String fileName) {
        File serverFolder = new File(new File(server.getExePath()).getParent());
        File logFolder = new File(serverFolder, "logs");
        return new File(logFolder, fileName);
    }

    public String searchLogFile(File log, String... words) {
        Set<String> wordSet = Arrays.stream(words)
                .map(String::toLowerCase)
                .filter(word -> pattern.matcher(word).matches())
                .collect(Collectors.toSet());
        StringBuilder sb = new StringBuilder(4096);

        try (BufferedReader reader = new BufferedReader(new FileReader(log))) {
           String row;
           while ((row = reader.readLine()) != null) {
               if (row.length() > 12) {
                   String lowerCaseRow = row.split(" - ", 2)[1].toLowerCase();
                   if (wordSet.stream().anyMatch(lowerCaseRow::contains)) {
                       sb.append(row);
                       sb.append(System.lineSeparator());
                   }
               }
           }
        }
        catch (Exception e) {
            e.printStackTrace();
        }

        return sb.toString();
    }
}
