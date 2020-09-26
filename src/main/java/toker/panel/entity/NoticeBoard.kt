package toker.panel.entity

import com.fasterxml.jackson.annotation.JsonView
import toker.panel.entity.NoticeBoard.View.Accesses
import toker.panel.entity.pk.NoticeBoardPK
import java.util.*
import javax.persistence.*

@Entity
@Table(name = "notice_board")
@IdClass(NoticeBoardPK::class)
data class NoticeBoard(
        @Id
        @Column(name = "index", nullable = false)
        var index: Int? = null,

        @Id
        @ManyToOne
        @JoinColumn(name = "server_id", referencedColumnName = "id", nullable = false)
        var server: Server? = null,

        @Column(name = "name", nullable = false, length = 64)
        var name: String? = null,

        @OneToMany(mappedBy = "board")
        @JsonView(Accesses::class)
        var accesses: MutableSet<NoticeBoardAccess>? = null,

        @OneToMany(mappedBy = "board")
        @JsonView(View.Entries::class)
        var entries: MutableSet<NoticeBoardEntry>? = null
) {
    interface View {
        interface Accesses
        interface Entries
        interface None
    }
}