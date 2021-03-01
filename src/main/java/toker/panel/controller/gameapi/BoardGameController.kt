package toker.panel.controller.gameapi

import org.springframework.data.crossstore.ChangeSetPersister.NotFoundException
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController
import toker.panel.entity.*
import toker.panel.entity.pk.NoticeBoardPK
import toker.panel.repository.NoticeBoardAccessRepository
import toker.panel.repository.NoticeBoardEntryRepository
import toker.panel.repository.NoticeBoardRepository
import toker.panel.service.PlayerService
import kotlin.math.max
import kotlin.math.min

@RestController
class BoardGameController(
    val boardRepository: NoticeBoardRepository,
    val accessRepository: NoticeBoardAccessRepository,
    val entryRepository: NoticeBoardEntryRepository,
    val playerService: PlayerService
) {

    @GetMapping("/gameapi/viewnoticeboard")
    fun view(boardid: Int, playerid: Int): String {
        val server = SecurityContextHolder.getContext().authentication.principal as Server
        val board = boardRepository.findOne { root, query, builder ->
            query.distinct(true)
            root.fetch(NoticeBoard_.entries)
            builder.and(
                    builder.equal(root.get(NoticeBoard_.index), boardid),
                    builder.equal(root.get(NoticeBoard_.server).get(Server_.id), server.id)
            )
        }.orElseThrow { NotFoundException() }

        val sb = StringBuilder("$playerid|")
        board.entries!!.forEach {
            sb.append("${it.entryNo}) ${it.entryText}$")
        }
        if (board.entries!!.isEmpty()) sb.append("No Entries")

        return sb.toString()
    }

    @GetMapping("/gameapi/giveboardaccess")
    fun grantAccess(boardid: Int, playerid: Int, playername: String, targetname: String): String {
        val server = SecurityContextHolder.getContext().authentication.principal as Server
        val owner = playerService.getPlayer(playername, server.id!!).orElseThrow { NotFoundException() }
        val board = boardRepository.getOne(NoticeBoardPK(boardid, server.id))
        val isOwner = accessRepository.countByBoardAndPlayerIdAndIsOwner(board, owner.id!!, isOwner = true) > 0
        if (isOwner) {
            val target = playerService.getPlayer(targetname, server.id!!).orElseThrow { NotFoundException() }
            accessRepository.saveAndFlush(NoticeBoardAccess(isOwner = false, board = board, player = target))
            return "$playerid|1"
        }
        return "$playerid|0"
    }

    @GetMapping("/gameapi/removeboardaccess")
    fun removeAccess(boardid: Int, playerid: Int, playername: String, targetname: String): String {
        val server = SecurityContextHolder.getContext().authentication.principal as Server
        val owner = playerService.getPlayer(playername, server.id!!).orElseThrow { NotFoundException() }
        val board = boardRepository.getOne(NoticeBoardPK(boardid, server.id))
        val isOwner = accessRepository.countByBoardAndPlayerIdAndIsOwner(board, owner.id!!, isOwner = true) > 0
        if (isOwner) {
            val target = playerService.getPlayer(targetname, server.id!!).orElseThrow { NotFoundException() }
            val accessList = accessRepository.findAll { root, _, builder ->
                builder.and(
                        builder.equal(root.get(NoticeBoardAccess_.board).get(NoticeBoard_.index), boardid),
                        builder.equal(root.get(NoticeBoardAccess_.player).get(Player_.id), target.id),
                        builder.equal(root.get(NoticeBoardAccess_.isOwner), false)
                )
            }
            accessRepository.deleteInBatch(accessList)
            return "$playerid|1"
        }
        return "$playerid|0"
    }

    @GetMapping("/gameapi/listboardaccess")
    fun listAccess(boardid: Int, playerid: Int, playername: String): String {
        val server = SecurityContextHolder.getContext().authentication.principal as Server
        val owner = playerService.getPlayer(playername, server.id!!).orElseThrow { NotFoundException() }
        val board = boardRepository.getOne(NoticeBoardPK(boardid, server.id))
        val hasAccess = accessRepository.countByBoardAndPlayerId(board, owner.id!!) > 0
        if (hasAccess) {
            val accessList = accessRepository.findAll { root, _, builder ->
                builder.equal(root.get(NoticeBoardAccess_.board).get(NoticeBoard_.index), boardid)
            }
            val result = accessList.map { it.player!!.name }.reduce { a, b -> "$a $b"}
            return "$playerid|$result"
        }
        return "$playerid|You are not a user of this notice board."
    }

    @GetMapping("/gameapi/editboardentry")
    fun editEntry(boardid: Int, playerid: Int, playername: String,
                  entryno: Int, content: String): String {
        val server = SecurityContextHolder.getContext().authentication.principal as Server
        val entryNo = max(min(entryno, 15), 1)
        val board = boardRepository.getOne(NoticeBoardPK(boardid, server.id))
        val entry = entryRepository.findAllByBoardAndEntryNo(board, entryNo)

        if (entry.isNotEmpty()) {
            return "$playerid|0|This row is not empty."
        }

        entryRepository.saveAndFlush(NoticeBoardEntry(board = board, entryNo = entryNo, entryText = content))

        return "$playerid|1|You edited the entry."
    }

    @GetMapping("/gameapi/removeboardentry")
    fun removeEntry(boardid: Int, playerid: Int, playername: String, entryno: Int): String {
        val server = SecurityContextHolder.getContext().authentication.principal as Server
        val entryNo = max(min(entryno, 15), 1)
        val owner = playerService.getPlayer(playername, server.id!!).orElseThrow { NotFoundException() }
        val board = boardRepository.getOne(NoticeBoardPK(boardid, server.id))
        val hasAccess = accessRepository.countByBoardAndPlayerId(board, owner.id!!) > 0
        if (hasAccess) {
            entryRepository.deleteByBoardAndEntryNo(board, entryNo)
            return "$playerid|You removed the entry."
        }
        return "$playerid|You have no access to this board."
    }
}