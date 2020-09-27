package toker.panel.controller.rest

import org.springframework.core.io.FileSystemResource
import org.springframework.data.crossstore.ChangeSetPersister
import org.springframework.http.CacheControl
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RestController
import toker.panel.authentication.JWTOpenIDAuthenticationToken
import toker.panel.bean.SelectedServerId
import toker.panel.entity.DownloadToken
import toker.panel.repository.DownloadTokenRepository
import toker.panel.repository.PanelUserRepository
import toker.panel.service.LogService
import toker.panel.service.ServerService
import java.io.File
import java.security.SecureRandom
import java.sql.Timestamp
import java.time.Instant
import java.util.*

@RestController
class LogController(private val logService: LogService,
                    private val serverService: ServerService,
                    private val tokenRepository: DownloadTokenRepository,
                    private val panelUserRepository: PanelUserRepository) {
    private val secureRandom = SecureRandom() //threadsafe
    private val base64Encoder = Base64.getUrlEncoder()

    @Throws(ChangeSetPersister.NotFoundException::class)
    @GetMapping("/api/log/list")
    fun getLogFiles(): Array<String>? {
        val server = serverService.getServer(SelectedServerId)
        return logService.getLogFileNames(server)
    }

    @GetMapping("/api/log/get/{fileName}")
    @Throws(ChangeSetPersister.NotFoundException::class)
    fun getLogFile(@PathVariable fileName: String): DownloadToken? {
        val server = serverService.getServer(SelectedServerId)
        val log = logService.getLogFile(server, fileName)
        val auth = SecurityContextHolder.getContext()
                .authentication as JWTOpenIDAuthenticationToken
        if (log.exists()) {
            val token = DownloadToken()
            token.file = log.absolutePath
            token.isUsed = false
            token.time = Timestamp.from(Instant.now())
            token.user = panelUserRepository.findByClaimedIdentity((auth.details as String))
            val randomBytes = ByteArray(24)
            secureRandom.nextBytes(randomBytes)
            val tokenStr = base64Encoder.encodeToString(randomBytes)
            token.token = tokenStr
            return tokenRepository.saveAndFlush(token)
        }
        return null
    }

    @GetMapping("/api/log/search/{fileName}")
    @Throws(ChangeSetPersister.NotFoundException::class)
    fun searchLogFile(@PathVariable fileName: String,
                      words: Array<String>): String? {
        val server = serverService.getServer(SelectedServerId)
        val log = logService.getLogFile(server, fileName)
        return if (log.exists()) {
            logService.searchLogFile(log, *words)
        } else null
    }

    @GetMapping(value = ["/download/{token}"], produces = [MediaType.APPLICATION_OCTET_STREAM_VALUE])
    fun download(@PathVariable token: String?): ResponseEntity<FileSystemResource> {
        val downloadToken = tokenRepository.findByToken(token!!).orElse(null)
        if (downloadToken != null && !downloadToken.isUsed!!) {
            downloadToken.isUsed = true
            tokenRepository.saveAndFlush(downloadToken)
            val log = File(downloadToken.file!!)
            if (log.exists()) {
                return ResponseEntity.ok()
                        .header("Content-Disposition", String.format("attachment; filename=\"%s\"", log.name))
                        .cacheControl(CacheControl.noCache())
                        .contentType(MediaType.APPLICATION_OCTET_STREAM)
                        .contentLength(log.length())
                        .body(FileSystemResource(log))
            }
        }
        return ResponseEntity.notFound().build()
    }
}