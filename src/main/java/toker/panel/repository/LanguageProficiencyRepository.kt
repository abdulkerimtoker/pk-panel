package toker.panel.repository;

import toker.panel.entity.LanguageProficiency;
import toker.panel.entity.pk.LanguageProficiencyPK;

public interface LanguageProficiencyRepository
        extends BaseRepository<LanguageProficiency, LanguageProficiencyPK> {
    void deleteByPlayerIdAndLanguageId(Integer playerId, Integer languageId);
}
