package toker.panel.repository

import org.springframework.data.jpa.repository.Modifying
import org.springframework.transaction.annotation.Transactional
import toker.panel.entity.LanguageProficiency
import toker.panel.entity.pk.LanguageProficiencyPK

interface LanguageProficiencyRepository : BaseRepository<LanguageProficiency, LanguageProficiencyPK> {
    @Transactional
    @Modifying
    fun deleteByPlayerIdAndLanguageId(playerId: Int, languageId: Int)
}