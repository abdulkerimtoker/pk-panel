package toker.panel.entity

import javax.persistence.*

@Entity
@Table(name = "notice_board_entry")
class NoticeBoardEntry(
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        @Column(name = "id", nullable = false)
        var id: Int? = null,

        @Column(name = "entry_text", nullable = false, length = 256)
        var entryText: String? = null,

        @Column(name = "entry_no", nullable = false)
        var entryNo: Int? = null,

        @ManyToOne
        @JoinColumns(JoinColumn(name = "board_index", referencedColumnName = "index", nullable = false), JoinColumn(name = "board_server_id", referencedColumnName = "server_id", nullable = false))
        var board: NoticeBoard? = null
)