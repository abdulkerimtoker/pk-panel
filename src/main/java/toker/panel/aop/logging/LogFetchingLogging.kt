package toker.panel.aop.logging

import com.fasterxml.jackson.databind.JsonNode

import toker.panel.repository.PanelUserRepository
import toker.panel.repository.LogRepository
import toker.panel.authentication.JWTOpenIDAuthenticationToken
import org.springframework.security.core.context.SecurityContextHolder
import javax.persistence.criteria.Root
import javax.persistence.criteria.CriteriaBuilder
import toker.panel.entity.PanelUser_
import com.fasterxml.jackson.databind.ObjectMapper
import org.aspectj.lang.annotation.Aspect
import org.aspectj.lang.annotation.Before
import org.aspectj.lang.annotation.Pointcut
import org.springframework.stereotype.Component
import toker.panel.bean.CurrentUser
import toker.panel.bean.SelectedServerId
import toker.panel.entity.Log
import toker.panel.entity.PanelUser
import toker.panel.repository.BaseRepository
import toker.panel.repository.ServerRepository
import java.util.*
import javax.inject.Inject
import javax.persistence.criteria.CriteriaQuery

@Aspect
@Component
class LogFetchingLogging(
    private val userRepo: PanelUserRepository,
    private val logRepo: LogRepository,
    private val serverRepository: ServerRepository
) {
    @Pointcut("target(toker.panel.controller.rest.LogController) && " +
            "execution(* searchLogFile(String, String[])) && args(fileName, words)")
    fun logFetching(fileName: String?, words: Array<String?>?) {
    }

    data class LogFetching(val logFile: String, val words: Array<String>)

    @Before("logFetching(fileName, words)")
    fun beforeLogFetching(fileName: String?, words: Array<String>?) {
        val fetching = LogFetching(fileName!!, words!!)
        val writer = ObjectMapper().writer()
        logRepo.saveAndFlush(Log(
            server = serverRepository.getOne(SelectedServerId),
            user = CurrentUser, type = Log.Type.LOG_FETCHING,
            data = writer.writeValueAsString(fetching)
        ))
    }
}