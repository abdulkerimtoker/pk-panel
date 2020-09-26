package toker.panel.entity.pk

import java.io.Serializable

data class ServerConfigurationPK(
        var server: Int? = null,
        var name: String? = null
) : Serializable