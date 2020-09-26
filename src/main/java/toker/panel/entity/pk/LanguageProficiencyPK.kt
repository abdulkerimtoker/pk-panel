package toker.panel.entity.pk

import java.io.Serializable

data class LanguageProficiencyPK(
        var player: Int? = null,
        var language: Int? = null
) : Serializable