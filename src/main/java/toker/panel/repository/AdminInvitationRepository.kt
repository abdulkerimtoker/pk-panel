package toker.panel.repository

import toker.panel.entity.AdminInvitation
import java.util.*

interface AdminInvitationRepository : BaseRepository<AdminInvitation, String> {
    fun findByCode(code: String): Optional<AdminInvitation>
}