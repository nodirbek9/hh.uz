package uz.java.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import uz.java.dto.resume.SkillRequest;
import uz.java.service.SkillService;

@RestController
@RequestMapping("/skills")
@RequiredArgsConstructor
public class SkillController {

    private final SkillService service;

    @PreAuthorize("hasAnyRole('ADMIN','EMPLOYER','JOB_SEEKER')")
    @GetMapping("/{id}")
    public ResponseEntity<?> getOne(@PathVariable Long id) {
        return ResponseEntity.ok(service.getOne(id));
    }

    @PreAuthorize("hasAnyRole('ADMIN','JOB_SEEKER')")
    @PostMapping
    public ResponseEntity<?> create(@RequestBody @Valid SkillRequest request) {
        return ResponseEntity.ok(service.create(request));
    }

    @PreAuthorize("hasAnyRole('ADMIN','JOB_SEEKER')")
    @PutMapping("/{id}")
    public ResponseEntity<?> update(@PathVariable Long id, @RequestBody SkillRequest request) {
        return ResponseEntity.ok(service.update(id, request));
    }

    @PreAuthorize("hasAnyRole('ADMIN','JOB_SEEKER')")
    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id) {
        return ResponseEntity.ok(service.delete(id));
    }

    @PreAuthorize("hasAnyRole('ADMIN','EMPLOYER','JOB_SEEKER')")
    @GetMapping
    public ResponseEntity<?> getAll(@RequestParam String search) {
        return ResponseEntity.ok(service.getAll(search));
    }
}
