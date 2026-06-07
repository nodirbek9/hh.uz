package uz.java.controller;

import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import uz.java.dto.resume.ResumeRequest;
import uz.java.service.ResumeService;

@RestController
@RequestMapping("/resumes")
public class ResumeController {

    private final ResumeService service;
    public ResumeController(ResumeService service) {
        this.service = service;
    }

    @PreAuthorize("hasAnyRole('ADMIN','EMPLOYER','JOB_SEEKER')")
    @GetMapping("/{id}")
    public ResponseEntity<?> getOne(@PathVariable Long id) {
        return ResponseEntity.ok(service.getOne(id));
    }

    @PreAuthorize("hasAnyRole('ADMIN', 'JOB_SEEKER')")
    @PostMapping
    public ResponseEntity<?> create(@RequestBody @Valid ResumeRequest request) {
        return ResponseEntity.ok(service.create(request));
    }

    @PreAuthorize("hasAnyRole('ADMIN','JOB_SEEKER')")
    @PutMapping("/{id}")
    public ResponseEntity<?> update(@PathVariable Long id, @RequestBody @Valid ResumeRequest request) {
        return ResponseEntity.ok(service.update(id, request));
    }
}
