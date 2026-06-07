package uz.java.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import uz.java.dto.resume.PortfolioRequest;
import uz.java.dto.resume.PortfolioResponse;
import uz.java.service.PortfolioService;

import java.util.List;

@RestController
@RequestMapping("/portfolios")
@RequiredArgsConstructor
public class PortfolioController {

    private final PortfolioService service;

    @PostMapping
    @PreAuthorize("hasAnyRole('ADMIN','JOB_SEEKER')")
    public ResponseEntity<Long> create(@RequestBody @Valid PortfolioRequest request) {
        return new ResponseEntity<>(service.create(request), HttpStatus.CREATED);
    }

    @GetMapping("/resume/{resumeId}")
    @PreAuthorize("hasAnyRole('ADMIN','EMPLOYER','JOB_SEEKER')")
    public ResponseEntity<List<PortfolioResponse>> getByResume(@PathVariable Long resumeId) {
        return ResponseEntity.ok(service.getByResume(resumeId));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN','JOB_SEEKER')")
    public ResponseEntity<Boolean> delete(@PathVariable Long id) {
        return ResponseEntity.ok(service.delete(id));
    }
}
