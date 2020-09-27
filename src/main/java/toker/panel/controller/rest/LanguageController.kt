package toker.panel.controller.rest

import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController
import toker.panel.entity.Language
import toker.panel.repository.LanguageRepository

@RestController
class LanguageController(private val languageRepo: LanguageRepository) {
    @GetMapping("/api/languages")
    fun languages(): List<Language> {
        return languageRepo.findAll()
    }
}