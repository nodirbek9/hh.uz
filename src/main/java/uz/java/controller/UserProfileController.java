package uz.java.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
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

    @PreAuthorize("hasAnyRole('ADMIN', 'JOB_SEEKER')")
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

    @PreAuthorize("hasAnyRole('ADMIN','JOB_SEEKER')")
    @GetMapping("/{id}")
    public ResponseEntity<?> getUserProfile(@PathVariable Long id) {
        return ResponseEntity.ok(userProfileService.getUserProfile(id));
    }

    @PreAuthorize("hasAnyRole('ADMIN','JOB_SEEKER')")
    @PutMapping("/{id}")
    public ResponseEntity<?> update(@PathVariable Long id, @RequestBody UserProfileRequest request){
        return ResponseEntity.ok(userProfileService.update(id, request));

    }

    @PreAuthorize("hasAnyRole('ADMIN','JOB_SEEKER')")
    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id) {
        return ResponseEntity.ok(userProfileService.delete(id));
    }

}
