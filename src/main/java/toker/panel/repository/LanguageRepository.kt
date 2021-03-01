package toker.panel.repository

import toker.panel.entity.Language
import java.util.*

interface LanguageRepository : BaseRepository<Language, Int> {
    fun findByNameIgnoreCase(name: String): Optional<Language>
}