package toker.panel.service

import org.springframework.stereotype.Service
import java.io.File
import java.io.FileInputStream
import java.util.*

@Service
class PanelConfigurationService {
    private val properties: Properties

    fun getProperty(key: String?): String {
        return properties.getProperty(key)
    }

    init {
        properties = Properties()
        val conf = File(File(System.getProperty("user.dir")), "config.txt")
        val inputStream = FileInputStream(conf)
        properties.load(inputStream)
        inputStream.close()
    }
}