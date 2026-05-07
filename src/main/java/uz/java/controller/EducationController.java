package uz.java.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import uz.java.dto.resume.EducationRequest;
import uz.java.service.EducationService;

@RestController
@RequestMapping("/education")
@RequiredArgsConstructor
public class EducationController {

    private final EducationService service;

    @GetMapping("/{id}")
    public ResponseEntity<?> getOne(@PathVariable Long id) {
        return ResponseEntity.ok(service.getOne(id));
    }

    @PostMapping
    public ResponseEntity<?> create(@RequestBody EducationRequest request) {
        return ResponseEntity.ok(service.create(request));
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> update(@PathVariable Long id, @RequestBody EducationRequest request){
        return ResponseEntity.ok(service.update(id, request));

    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Boolean> delete(@PathVariable Long id){
        return ResponseEntity.ok(service.delete(id));
    }

    @GetMapping("/get-by-name")
    public ResponseEntity<?> findByName(@RequestParam String name){
        return ResponseEntity.ok(service.getByName(name));
    }
}
