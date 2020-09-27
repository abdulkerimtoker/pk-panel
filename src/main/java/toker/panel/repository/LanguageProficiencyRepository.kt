package toker.panel.repository

import toker.panel.entity.LanguageProficiency
import toker.panel.entity.pk.LanguageProficiencyPK

interface LanguageProficiencyRepository : BaseRepository<LanguageProficiency, LanguageProficiencyPK> {
    fun deleteByPlayerIdAndLanguageId(playerId: Int, languageId: Int)
}