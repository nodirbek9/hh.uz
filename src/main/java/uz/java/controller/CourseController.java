package uz.java.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import uz.java.dto.resume.CourseFilter;
import uz.java.dto.resume.CourseRequest;
import uz.java.dto.resume.CourseResponse;
import uz.java.service.CourseService;

import java.util.List;

@RestController
@RequestMapping("/courses")
@RequiredArgsConstructor
public class CourseController {

    private final CourseService courseService;

    @PreAuthorize("hasAnyRole('ADMIN','EMPLOYER','JOB_SEEKER')")
    @GetMapping("/{id}")
    public ResponseEntity<CourseResponse> getOne(@PathVariable Long id) {
        return ResponseEntity.ok(courseService.getOne(id));
    }

    @PreAuthorize("hasAnyRole('ADMIN', 'JOB_SEEKER')")
    @PostMapping
    public ResponseEntity<?> create(@RequestBody @Valid CourseRequest request) {
        return new ResponseEntity<>(courseService.create(request), HttpStatus.CREATED);
    }

    @PreAuthorize("hasAnyRole('ADMIN','JOB_SEEKER')")
    @PutMapping("/{id}")
    public ResponseEntity<?> update(@PathVariable Long id, @RequestBody CourseRequest request) {
        return new ResponseEntity<>(courseService.update(id, request), HttpStatus.OK);
    }

    @PreAuthorize("hasAnyRole('ADMIN', 'JOB_SEEKER')")
    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id) {
        return ResponseEntity.ok(courseService.delete(id));
    }

    @PreAuthorize("hasAnyRole('ADMIN','EMPLOYER','JOB_SEEKER')")
    @GetMapping
    public ResponseEntity<?> getAll(@RequestParam Integer page,
                                    @RequestParam Integer limit,
                                    @RequestParam String sortBy,
                                    @RequestParam(required = false) List<Long> resumeId,
                                    @RequestParam(required = false) String name,
                                    @RequestParam(required = false) String organization,
                                    @RequestParam(required = false) String specialisation,
                                    @RequestParam(required = false) String graduationYear) {
        return ResponseEntity.ok(courseService.getAll(new CourseFilter(page, limit, sortBy, resumeId, name,
                organization, specialisation, graduationYear)));
    }

}