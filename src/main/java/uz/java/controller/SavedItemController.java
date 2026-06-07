package uz.java.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import uz.java.config.CustomUserDetails;
import uz.java.dto.resume.ResumeResponse;
import uz.java.dto.vacancy.VacancyResponse;
import uz.java.service.SavedItemService;

import java.util.List;

@RestController
@RequestMapping("/saved")
@RequiredArgsConstructor
public class SavedItemController {

    private final SavedItemService service;

    @PostMapping("/vacancies/{vacancyId}")
    @PreAuthorize("hasRole('JOB_SEEKER')")
    public ResponseEntity<Long> saveVacancy(
            @PathVariable Long vacancyId,
            @AuthenticationPrincipal CustomUserDetails principal) {
        return new ResponseEntity<>(service.saveVacancy(vacancyId, principal.getUserId()), HttpStatus.CREATED);
    }

    @DeleteMapping("/vacancies/{vacancyId}")
    @PreAuthorize("hasRole('JOB_SEEKER')")
    public ResponseEntity<Boolean> removeSavedVacancy(
            @PathVariable Long vacancyId,
            @AuthenticationPrincipal CustomUserDetails principal) {
        return ResponseEntity.ok(service.removeSavedVacancy(vacancyId, principal.getUserId()));
    }

    @GetMapping("/vacancies")
    @PreAuthorize("hasRole('JOB_SEEKER')")
    public ResponseEntity<List<VacancyResponse>> getMySavedVacancies(
            @AuthenticationPrincipal CustomUserDetails principal) {
        return ResponseEntity.ok(service.getMySavedVacancies(principal.getUserId()));
    }

    @PostMapping("/resumes/{resumeId}")
    @PreAuthorize("hasAnyRole('ADMIN','EMPLOYER')")
    public ResponseEntity<Long> saveResume(
            @PathVariable Long resumeId,
            @AuthenticationPrincipal CustomUserDetails principal) {
        return new ResponseEntity<>(service.saveResume(resumeId, principal.getUserId()), HttpStatus.CREATED);
    }

    @GetMapping("/resumes")
    @PreAuthorize("hasAnyRole('ADMIN','EMPLOYER')")
    public ResponseEntity<List<ResumeResponse>> getMySavedResumes(
            @AuthenticationPrincipal CustomUserDetails principal) {
        return ResponseEntity.ok(service.getMySavedResumes(principal.getUserId()));
    }
}
