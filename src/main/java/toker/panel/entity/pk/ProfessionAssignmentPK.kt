package toker.panel.entity.pk

import java.io.Serializable

data class ProfessionAssignmentPK(
        var profession: Int? = null,
        var player: Int? = null
) : Serializable