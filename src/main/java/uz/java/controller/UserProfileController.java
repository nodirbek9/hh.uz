package uz.java.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import uz.java.dto.user.UserProfileRequest;
import uz.java.entity.enums.CompletedResume;
import uz.java.service.UserProfileService;

import java.util.Arrays;
import java.util.List;
import java.util.Locale;

@RestController
@RequestMapping("/userprofiles")
@RequiredArgsConstructor
public class UserProfileController {

    private final UserProfileService userProfileService;

    @GetMapping
    public List<String> getAll() {
        Locale locale = LocaleContextHolder.getLocale();
        String language = locale.getLanguage();

        return Arrays.stream(CompletedResume.values())
                .map(enumValue -> getLocalizedValue(enumValue, language))
                .toList();
    }

    private String getLocalizedValue(CompletedResume resume, String language) {
        return switch (language) {
            case "uz" -> resume.uz;
            case "ru" -> resume.ru;
            case "en" -> resume.en;
            default -> resume.en;  // default ingliz
        };
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getUserProfile(@PathVariable Long id) {
        return ResponseEntity.ok(userProfileService.getUserProfile(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> update(@PathVariable Long id, @RequestBody UserProfileRequest request){
        return ResponseEntity.ok(userProfileService.update(id, request));

    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id) {
        return ResponseEntity.ok(userProfileService.delete(id));
    }

}
