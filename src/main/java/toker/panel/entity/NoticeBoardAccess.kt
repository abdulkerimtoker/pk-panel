package toker.panel.entity

import com.fasterxml.jackson.annotation.JsonView
import toker.panel.entity.NoticeBoardAccess.View.Board
import java.util.*
import javax.persistence.*

@Entity
@Table(name = "notice_board_access")
data class NoticeBoardAccess(
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        @Column(name = "id", nullable = false)
        var id: Int? = null,

        @Column(name = "is_owner", nullable = false)
        var isOwner: Boolean? = false,

        @ManyToOne
        @JoinColumns(JoinColumn(name = "board_index", referencedColumnName = "index", nullable = false), JoinColumn(name = "board_server_id", referencedColumnName = "server_id", nullable = false))
        @JsonView(Board::class)
        var board: NoticeBoard? = null,

        @ManyToOne
        @JoinColumn(name = "user_id", referencedColumnName = "id", nullable = false)
        @JsonView(View.Player::class)
        var player: Player? = null
) {
    interface View {
        interface Board
        interface Player
    }
}