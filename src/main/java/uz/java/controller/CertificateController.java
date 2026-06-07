package uz.java.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import uz.java.dto.resume.CertificateFilter;
import uz.java.dto.resume.CertificateRequest;
import uz.java.service.CertificateService;

@RestController
@RequestMapping("/certificates")
@RequiredArgsConstructor
public class CertificateController {

    private final CertificateService service;

    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('JOB_SEEKER', 'EMPLOYER', 'ADMIN')")
    public ResponseEntity<?> getOne(@PathVariable Long id) {
        return ResponseEntity.ok(service.getOne(id));
    }

    @PostMapping
    @PreAuthorize("hasAnyRole('JOB_SEEKER', 'ADMIN')")
    public ResponseEntity<?> create(@RequestBody @Valid CertificateRequest request) {
        return ResponseEntity.ok(service.create(request));
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAnyRole('JOB_SEEKER', 'ADMIN')")
    public ResponseEntity<?> update(@PathVariable Long id, @RequestBody CertificateRequest request) {
        return ResponseEntity.ok(service.update(id, request));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAnyRole('JOB_SEEKER', 'ADMIN')")
    public ResponseEntity<?> delete(@PathVariable Long id) {
        return ResponseEntity.ok(service.delete(id));
    }

    @GetMapping
    @PreAuthorize("hasAnyRole('JOB_SEEKER', 'EMPLOYER', 'ADMIN')")
    public ResponseEntity<?> getAll(@RequestParam(required = false) Integer page,
                                    @RequestParam(required = false) Integer limit,
                                    @RequestParam(required = false) String sortBy,
                                    @RequestParam(required = false) String name) {
        return ResponseEntity.ok(service.getAll(new CertificateFilter(page, limit, sortBy, name)));
    }
}
