package toker.panel.entity.pk

import java.io.Serializable

data class ChestSlotPK(
        var slot: Int? = null,
        var chest: ChestPK? = null
) : Serializable