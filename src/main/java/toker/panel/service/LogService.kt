package toker.panel.service

import org.springframework.scheduling.annotation.Scheduled
import org.springframework.stereotype.Service
import toker.panel.entity.Server
import java.io.BufferedReader
import java.io.File
import java.io.FileReader
import java.text.SimpleDateFormat
import java.util.*
import java.util.regex.Pattern
import java.util.stream.Collectors

@Service
class LogService {

    private val searchPattern = Pattern.compile("[\\w _\\-\\[\\]:()]{3,128}")
    private val fileNamePattern = Pattern.compile("server_log_(?<day>\\d{2})_(?<month>\\d{2})_(?<year>\\d{2})\\.txt")
    private val dateFormat = SimpleDateFormat("dd MM YY")

    fun getLogFileNames(server: Server): Array<String> {
        val serverFolder = File(File(server.exePath!!).parent)
        val logFolder = File(serverFolder, "logs")
        val list = logFolder.list()
        return list ?: emptyArray()
    }

    fun getLogFile(server: Server, fileName: String): File {
        val serverFolder = File(File(server.exePath!!).parent)
        val logFolder = File(serverFolder, "logs")
        return File(logFolder, fileName)
    }

    fun getLogArchiveFolder(server: Server): File {
        val serverFolder = File(File(server.exePath!!).parent)
        val archiveFolder = File(serverFolder, "archived-logs")
        if (!archiveFolder.exists())
            archiveFolder.mkdir()
        return archiveFolder
    }

    @Scheduled(fixedDelay = 1000 * 60)
    fun archiveLogFiles() {

    }

    fun searchLogFile(log: File, vararg words: String): String {
        val wordSet = Arrays.stream(words)
                .map { obj: String -> obj.toLowerCase() }
                .filter { word: String -> searchPattern.matcher(word).matches() }
                .collect(Collectors.toSet())
        val sb = StringBuilder(4096)
        try {
            BufferedReader(FileReader(log)).use { reader ->
                var row: String
                while (reader.readLine().also { row = it } != null) {
                    if (row.length > 12) {
                        val lowerCaseRow = row.split(" - ", limit = 2)[1].toLowerCase()
                        if (wordSet.stream().anyMatch { s: String -> lowerCaseRow.contains(s) }) {
                            sb.append(row)
                            sb.append(System.lineSeparator())
                        }
                    }
                }
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return sb.toString()
    }
}