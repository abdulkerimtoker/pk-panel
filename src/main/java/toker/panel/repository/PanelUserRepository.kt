package toker.panel.repository

import toker.panel.entity.PanelUser

interface PanelUserRepository : BaseRepository<PanelUser, Int> {
    fun findByUsername(username: String): PanelUser
    fun findByClaimedIdentity(claimedIdentity: String): PanelUser
}