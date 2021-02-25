package toker.panel.controller.gameapi

import org.springframework.data.crossstore.ChangeSetPersister
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController
import toker.panel.entity.Letter
import toker.panel.entity.SealState
import toker.panel.entity.Server
import toker.panel.repository.LanguageRepository
import toker.panel.repository.LetterRepository
import kotlin.math.floor

@RestController
class LetterController(
    val letterRepository: LetterRepository,
    val languageRepository: LanguageRepository
) {

    @GetMapping("/gameapi/createletter")
    fun create(playerid: Int, lettertitle: String): String {
        val server = SecurityContextHolder.getContext().authentication.principal as Server
        var letter = Letter(server = server, language = languageRepository.getOne(1), title = lettertitle)
        letter = letterRepository.saveAndFlush(letter)
        return "$playerid|${letter.id}|You created a letter with the title: $lettertitle"
    }

    @GetMapping("/gameapi/readletter")
    fun read(playerid: Int, letterid: Int): String {
        val server = SecurityContextHolder.getContext().authentication.principal as Server
        val letter = letterRepository.findById(letterid).orElseThrow { ChangeSetPersister.NotFoundException() }

        if (letter.server != server) {
            return "-1"
        }

        if (letter.sealState == SealState.SEALED) {
            return "$playerid|1|This letter is sealed."
        }

        val lineLimit = 75
        val sb = StringBuilder("$playerid")
        var lineCount = 0

        letter.text.split("$").forEach {
            val partsCount = floor((it.length / lineLimit).toDouble()).toInt()
            if (it.length < lineLimit) {
                sb.append("|[$it")
            }
            else {
                for (i in 0 until partsCount) {
                    sb.append("|[${it.substring(i * lineLimit, i * lineLimit + lineLimit)}")
                    lineCount++
                }
                sb.append("|[${it.substring(partsCount * lineLimit, partsCount * lineLimit + it.length % lineLimit)}")
                lineCount++
            }
        }
        sb.append("|$lineCount")
        sb.append("|${letter.language.id}")

        return sb.toString()
    }

    @GetMapping("/gameapi/letterinfo")
    fun info(playerid: Int, letterid: Int): String {
        val server = SecurityContextHolder.getContext().authentication.principal as Server
        val letter = letterRepository.findById(letterid).orElseThrow { ChangeSetPersister.NotFoundException() }

        if (letter.server != server) {
            return "-1"
        }

        return "$playerid|$letterid|${letter.title} [${letter.sealState.text}]"
    }

    @GetMapping("/gameapi/appendtoletter")
    fun append(playerid: Int, letterid: Int, appendtext: String): String {
        val server = SecurityContextHolder.getContext().authentication.principal as Server
        val letter = letterRepository.findById(letterid).orElseThrow { ChangeSetPersister.NotFoundException() }

        if (letter.server != server) {
            return "-1"
        }

        if (letter.sealState == SealState.SEALED) {
            return "$playerid|This letter is sealed."
        }

        val presentLineCount = letter.text.split("$").size
        val addLineCount = appendtext.split("$").size

        if (presentLineCount + addLineCount > 5) {
            return "$playerid|The max. amount of line breaks in a letter is 5 and the amount you are attempting to insert exceeds the limit."
        }

        letter.text = letter.text + appendtext
        letterRepository.saveAndFlush(letter)

        return "$playerid|You appended the text to the letter."
    }

    @GetMapping("/gameapi/sealletter")
    fun seal(playerid: Int, letterid: Int): String {
        val server = SecurityContextHolder.getContext().authentication.principal as Server
        val letter = letterRepository.findById(letterid).orElseThrow { ChangeSetPersister.NotFoundException() }

        if (letter.server != server) {
            return "-1"
        }

        if (letter.sealState in arrayOf(SealState.SEALED, SealState.UNSEALED)) {
            return "$playerid|This letter was already sealed or unsealed."
        }

        letter.sealState = SealState.SEALED
        letterRepository.saveAndFlush(letter)

        return "$playerid|You sealed the letter."
    }

    @GetMapping("/gameapi/unsealletter")
    fun unseal(playerid: Int, letterid: Int): String {
        val server = SecurityContextHolder.getContext().authentication.principal as Server
        val letter = letterRepository.findById(letterid).orElseThrow { ChangeSetPersister.NotFoundException() }

        if (letter.server != server) {
            return "-1"
        }

        if (letter.sealState in arrayOf(SealState.NOT_SEALED, SealState.UNSEALED)) {
            return "$playerid|This letter was not sealed."
        }

        letter.sealState = SealState.UNSEALED
        letterRepository.saveAndFlush(letter)

        return "$playerid|You unsealed the letter."
    }
}