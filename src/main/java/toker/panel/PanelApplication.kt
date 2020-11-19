package toker.panel

import org.springframework.boot.CommandLineRunner
import org.springframework.boot.SpringApplication
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.context.annotation.ImportResource
import org.springframework.data.jpa.repository.config.EnableJpaRepositories
import org.springframework.scheduling.annotation.EnableScheduling
import org.springframework.scheduling.annotation.Scheduled
import toker.panel.aop.Test
import toker.panel.authentication.EndedSessions
import toker.panel.entity.PanelUserSession
import toker.panel.entity.PanelUserSession_
import toker.panel.entity.Player
import toker.panel.repository.BaseRepository
import java.io.File
import java.io.FileInputStream
import java.util.*
import java.util.stream.Collectors
import javax.persistence.criteria.CriteriaBuilder
import javax.persistence.criteria.CriteriaQuery
import javax.persistence.criteria.Root

@SpringBootApplication
@ImportResource("classpath*:injections.xml")
@EnableJpaRepositories
@EnableScheduling
class PanelApplication(private val sessionRepo: BaseRepository<PanelUserSession?, Int>) : CommandLineRunner {

    override fun run(vararg args: String) {
        val endedSessions = sessionRepo.findAll { root: Root<PanelUserSession?>, _, builder: CriteriaBuilder ->
            builder.equal(root.get(PanelUserSession_.isEnded), true) }
        EndedSessions.endSessions(endedSessions.stream().map { it!!.id }.collect(Collectors.toList()))
        Test.sea()
    }

    @Scheduled(fixedDelay = 5 * 1000)
    fun endSessionsPeriodically() {
        val endedSessions = sessionRepo.findAll { root: Root<PanelUserSession?>, _, builder: CriteriaBuilder ->
            builder.equal(root.get(PanelUserSession_.isEnded), true) }
        EndedSessions.endSessions(endedSessions.stream().map { it!!.id }.collect(Collectors.toList()))
    }

    companion object {
        @Throws(Exception::class)
        @JvmStatic
        fun main(args: Array<String>) {
            val properties = Properties()
            val conf = File(File(System.getProperty("user.dir")), "config.txt")
            val inputStream = FileInputStream(conf)
            properties.load(inputStream)
            inputStream.close()
            if (properties.containsKey("server.port")) {
                System.setProperty("server.port", properties.getProperty("server.port"))
            } else {
                System.setProperty("server.port", "8080")
            }
            val compulsoryConfigs = arrayOf(
                    "PANEL_DB_HOST", "PANEL_DB_PORT",
                    "PANEL_DB_NAME", "PANEL_DB_USER",
                    "PANEL_DB_USER_PASS"
            )
            for (config in compulsoryConfigs) {
                if (!properties.containsKey(config)) {
                    println(String.format("Config option %s is required for the panel to start. Please edit config.txt and make sure its value is set.", config))
                    return
                }
            }
            SpringApplication.run(PanelApplication::class.java, *args)
        }
    }
}