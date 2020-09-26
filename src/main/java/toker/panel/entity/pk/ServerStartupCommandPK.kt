package toker.panel.entity.pk

import java.io.Serializable

data class ServerStartupCommandPK(
        var server: Int? = null,
        var command: String? = null
) : Serializable