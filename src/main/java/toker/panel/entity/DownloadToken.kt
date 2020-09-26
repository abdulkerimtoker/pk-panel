package toker.panel.entity

import com.fasterxml.jackson.annotation.JsonIgnore
import java.sql.Timestamp
import javax.persistence.*

@Entity
@Table(name = "download_token")
data class DownloadToken(
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        @Column(name = "id")
        var id: Int? = null,

        @Column(name = "token", nullable = false)
        var token: String? = null,

        @JsonIgnore
        @Column(name = "file", nullable = false)
        var file: String? = null,

        @ManyToOne(fetch = FetchType.EAGER)
        @JoinColumn(name = "user_id", referencedColumnName = "id", nullable = false)
        @JsonIgnore
        var user: PanelUser? = null,

        @Column(name = "time", nullable = false)
        var time: Timestamp? = null,

        @Column(name = "is_used", nullable = false)
        var isUsed: Boolean? = null
)