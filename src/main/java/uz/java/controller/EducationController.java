package uz.java.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import uz.java.dto.resume.EducationFilter;
import uz.java.dto.resume.EducationRequest;
import uz.java.service.EducationService;

@RestController
@RequestMapping("/education")
@RequiredArgsConstructor
public class EducationController {

    private final EducationService service;

    @PreAuthorize("hasAnyRole('ADMIN','EMPLOYER','JOB_SEEKER')")
    @GetMapping("/{id}")
    public ResponseEntity<?> getOne(@PathVariable Long id) {
        return ResponseEntity.ok(service.getOne(id));
    }

    @PreAuthorize("hasAnyRole('ADMIN','JOB_SEEKER')")
    @PostMapping
    public ResponseEntity<?> create(@RequestBody EducationRequest request) {
        return ResponseEntity.ok(service.create(request));
    }

    @PreAuthorize("hasAnyRole('ADMIN','JOB_SEEKER')")
    @PutMapping("/{id}")
    public ResponseEntity<?> update(@PathVariable Long id, @RequestBody EducationRequest request){
        return ResponseEntity.ok(service.update(id, request));

    }

    @PreAuthorize("hasAnyRole('ADMIN', 'JOB_SEEKER')")
    @DeleteMapping("/{id}")
    public ResponseEntity<Boolean> delete(@PathVariable Long id){
        return ResponseEntity.ok(service.delete(id));
    }

    @PreAuthorize("hasAnyRole('ADMIN','EMPLOYER','JOB_SEEKER')")
    @GetMapping("/get-by-name")
    public ResponseEntity<?> findByName(@RequestParam String name){
        return ResponseEntity.ok(service.getByName(name));
    }

    @PreAuthorize("hasAnyRole('ADMIN','EMPLOYER','JOB_SEEKER')")
    @GetMapping
    public ResponseEntity<?> getAll(@RequestParam(required = false) Integer page,
                                        @RequestParam(required = false) Integer limit,
                                        @RequestParam(required = false) String sortBy,
                                        @RequestParam(required = false) String name){
        return ResponseEntity.ok(service.getAll(new EducationFilter(page, limit, sortBy, name)));
    }
}
