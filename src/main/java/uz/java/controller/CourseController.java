package uz.java.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import uz.java.dto.resume.CourseRequest;
import uz.java.dto.resume.CourseResponse;
import uz.java.service.CourseService;

@RestController
@RequestMapping("/courses")
@RequiredArgsConstructor
public class CourseController {

    private final CourseService courseService;

    @GetMapping("/{id}")
    public ResponseEntity<CourseResponse> getOne(@PathVariable Long id) {
        return ResponseEntity.ok(courseService.getOne(id));
    }

    @PostMapping
    public ResponseEntity<?> create(@RequestBody @Valid CourseRequest request) {
        return new ResponseEntity<>(courseService.create(request), HttpStatus.CREATED);
    }
    @PutMapping("/{id}")
    public ResponseEntity<?> update(@PathVariable Long id, @RequestBody CourseRequest request){
        return new ResponseEntity<>(courseService.update(id, request), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id){
        return ResponseEntity.ok(courseService.delete(id));
    }

    @GetMapping
    public ResponseEntity<?> getAll(/*@RequestParam Integer page,
                                    @RequestParam Integer limit,
                                    @RequestParam String sortBy,
                                    @RequestParam List<Long> resumeId,
                                    @RequestParam String name,
                                    @RequestParam String organization,
                                    @RequestParam String specialisation,
                                    @RequestParam String graduationYear*/) {
        return ResponseEntity.ok(courseService.getAll());
    }

}