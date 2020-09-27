package toker.panel.controller.rest;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import toker.panel.entity.Language;
import toker.panel.repository.LanguageRepository;

import java.util.List;

@RestController
public class LanguageController {

    private LanguageRepository languageRepo;

    public LanguageController(LanguageRepository languageRepo) {
        this.languageRepo = languageRepo;
    }

    @GetMapping("/api/languages")
    public List<Language> languages() {
        return languageRepo.findAll();
    }
}
