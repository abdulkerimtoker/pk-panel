package toker.panel.repository

import toker.panel.entity.DownloadToken
import java.util.*

interface DownloadTokenRepository : BaseRepository<DownloadToken, Int> {
    fun findByToken(token: String): Optional<DownloadToken>
}