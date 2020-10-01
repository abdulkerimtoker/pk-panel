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
import toker.panel.entity.Log
import toker.panel.entity.PanelUser
import java.util.*
import javax.inject.Inject
import javax.persistence.criteria.CriteriaQuery

@Aspect
class LogFetchingLogging {
    private var userRepo: PanelUserRepository? = null
    private var logRepo: LogRepository? = null
    @Inject
    fun setUserRepo(userRepo: PanelUserRepository?) {
        this.userRepo = userRepo
    }

    @Inject
    fun setLogRepo(logRepo: LogRepository?) {
        this.logRepo = logRepo
    }

    @Pointcut("target(toker.panel.controller.rest.LogController) && " +
            "execution(* searchLogFile(String, String[])) && args(fileName, words)")
    fun logFetching(fileName: String?, words: Array<String?>?) {
    }

    @Before("logFetching(fileName, words)")
    fun beforeLogFetching(fileName: String?, words: Array<String>?) {
        val auth = SecurityContextHolder.getContext()
                .authentication as JWTOpenIDAuthenticationToken
        val user = userRepo!!.findOne { root: Root<PanelUser?>, _, builder: CriteriaBuilder -> builder.equal(root.get(PanelUser_.claimedIdentity), auth.details) }
                .orElseThrow()
        val mapper = ObjectMapper()
        val node = mapper.createObjectNode()
        node.put("logFile", fileName)
        node.put("serverId", auth.selectedServerId)
        val wordsNode = mapper.createArrayNode()
        Arrays.stream(words).filter { word: String -> !word.isBlank() }.forEach { v: String? -> wordsNode.add(v) }
        node.set<JsonNode>("words", wordsNode)
        val panelLog = Log()
        panelLog.type = Log.Type.LOG_FETCHING
        panelLog.user = user
        panelLog.data = node.toString()
        logRepo!!.save(panelLog)
    }
}