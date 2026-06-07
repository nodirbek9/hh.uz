package uz.java.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import uz.java.config.CustomUserDetails;
import uz.java.dto.application.ApplicationRequest;
import uz.java.dto.application.ApplicationResponse;
import uz.java.dto.application.ApplicationStatusRequest;
import uz.java.service.VacancyApplicationService;

import java.util.List;

@RestController
@RequestMapping("/applications")
@RequiredArgsConstructor
public class VacancyApplicationController {

    private final VacancyApplicationService service;

    @PostMapping
    @PreAuthorize("hasRole('JOB_SEEKER')")
    public ResponseEntity<Long> apply (
            @RequestBody @Valid ApplicationRequest request,
            @AuthenticationPrincipal CustomUserDetails principal) {
        return new ResponseEntity<>(service.apply(request, principal.getUserId()), HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN','EMPLOYER','JOB_SEEKER')")
    public ResponseEntity<ApplicationResponse> getOne(@PathVariable Long id) {
        return ResponseEntity.ok(service.getOne(id));
    }

    @GetMapping("/my")
    @PreAuthorize("hasRole('JOB_SEEKER')")
    public ResponseEntity<List<ApplicationResponse>> getMyApplications(
            @AuthenticationPrincipal CustomUserDetails principal) {
        return ResponseEntity.ok(service.getMyApplication(principal.getUserId()));
    }

    @GetMapping("/vacancy/{vacancyId}")
    @PreAuthorize("hasAnyRole('ADMIN','EMPLOYER')")
    public ResponseEntity<List<ApplicationResponse>> getByVacancy(@PathVariable Long vacancyId) {
        return ResponseEntity.ok(service.getByVacancy(vacancyId));
    }

    @PutMapping("/{id}/status")
    @PreAuthorize("hasAnyRole('ADMIN','EMPLOYER')")
    public ResponseEntity<Boolean> updateStatus(
            @PathVariable Long id,
            @RequestBody ApplicationStatusRequest request) {
        return ResponseEntity.ok(service.updateStatus(id, request));
    }
}
