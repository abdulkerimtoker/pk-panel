package toker.panel.entity.pk

import java.io.Serializable

data class InventorySlotPK(
        var slot: Int? = null,
        var inventory: Int? = null
) : Serializable