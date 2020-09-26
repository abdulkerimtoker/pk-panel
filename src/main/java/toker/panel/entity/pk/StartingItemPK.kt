package toker.panel.entity.pk

import java.io.Serializable

data class StartingItemPK(
        var server: Int? = null,
        var item: Int? = null
) : Serializable