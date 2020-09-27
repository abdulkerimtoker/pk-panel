package toker.panel.controller.rest;

import org.springframework.core.io.FileSystemResource;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.http.CacheControl;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import toker.panel.authentication.JWTOpenIDAuthenticationToken;
import toker.panel.bean.SelectedServerId;
import toker.panel.entity.DownloadToken;
import toker.panel.entity.Server;
import toker.panel.repository.DownloadTokenRepository;
import toker.panel.repository.PanelUserRepository;
import toker.panel.service.LogService;
import toker.panel.service.ServerService;

import java.io.File;
import java.security.SecureRandom;
import java.sql.Timestamp;
import java.time.Instant;
import java.util.Base64;

@RestController
public class LogController {

    private LogService logService;
    private ServerService serverService;

    private DownloadTokenRepository tokenRepository;
    private PanelUserRepository panelUserRepository;

    private final SecureRandom secureRandom = new SecureRandom(); //threadsafe
    private final Base64.Encoder base64Encoder = Base64.getUrlEncoder();

    public LogController(LogService logService,
                         ServerService serverService,
                         DownloadTokenRepository tokenRepository,
                         PanelUserRepository panelUserRepository) {
        this.logService = logService;
        this.serverService = serverService;
        this.tokenRepository = tokenRepository;
        this.panelUserRepository = panelUserRepository;
    }

    @GetMapping("/api/log/list")
    public String[] getLogFiles() throws ChangeSetPersister.NotFoundException {
        Server server = serverService.getServer(SelectedServerId.get());
        return logService.getLogFileNames(server);
    }

    @GetMapping("/api/log/get/{fileName}")
    public DownloadToken getLogFile(@PathVariable String fileName)
            throws ChangeSetPersister.NotFoundException {
        Server server = serverService.getServer(SelectedServerId.get());
        File log = logService.getLogFile(server, fileName);

        JWTOpenIDAuthenticationToken auth =
                (JWTOpenIDAuthenticationToken) SecurityContextHolder.getContext()
                .getAuthentication();

        if (log.exists()) {
            DownloadToken token = new DownloadToken();
            token.setFile(log.getAbsolutePath());
            token.setUsed(false);
            token.setTime(Timestamp.from(Instant.now()));
            token.setUser(panelUserRepository.findByClaimedIdentity((String)auth.getDetails()));

            byte[] randomBytes = new byte[24];
            secureRandom.nextBytes(randomBytes);
            String tokenStr = base64Encoder.encodeToString(randomBytes);

            token.setToken(tokenStr);

            return tokenRepository.saveAndFlush(token);
        }

        return null;
    }

    @GetMapping("/api/log/search/{fileName}")
    public String searchLogFile(@PathVariable String fileName,
                                String[] words)
            throws ChangeSetPersister.NotFoundException {
        Server server = serverService.getServer(SelectedServerId.get());
        File log = logService.getLogFile(server, fileName);

        if (log.exists()) {
            return logService.searchLogFile(log, words);
        }

        return null;
    }

    @GetMapping(value = "/download/{token}", produces = MediaType.APPLICATION_OCTET_STREAM_VALUE)
    public ResponseEntity<FileSystemResource> download(@PathVariable String token) {
        DownloadToken downloadToken = tokenRepository.findByToken(token).orElse(null);

        if (downloadToken != null && !downloadToken.isUsed()) {
            downloadToken.setUsed(true);
            tokenRepository.saveAndFlush(downloadToken);

            File log = new File(downloadToken.getFile());

            if (log.exists()) {
                return ResponseEntity.ok()
                        .header("Content-Disposition",
                                String.format("attachment; filename=\"%s\"", log.getName()))
                        .cacheControl(CacheControl.noCache())
                        .contentType(MediaType.APPLICATION_OCTET_STREAM)
                        .contentLength(log.length())
                        .body(new FileSystemResource(log));
            }
        }

        return ResponseEntity.notFound().build();
    }
}
