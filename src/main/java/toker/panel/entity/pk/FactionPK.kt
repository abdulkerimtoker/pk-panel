package toker.panel.entity.pk

import java.io.Serializable

data class FactionPK(
        var index: Int? = null,
        var server: Int? = null
) : Serializable