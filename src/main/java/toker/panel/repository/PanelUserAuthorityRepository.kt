package toker.panel.repository

import toker.panel.entity.PanelUserAuthority
import java.util.*

interface PanelUserAuthorityRepository : BaseRepository<PanelUserAuthority, Int> {
    fun findByAuthorityName(authorityName: String): Optional<PanelUserAuthority>
}