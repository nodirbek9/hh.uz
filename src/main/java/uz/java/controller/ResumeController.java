package uz.java.controller;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.function.EntityResponse;
import uz.java.dto.resume.ResumeRequest;
import uz.java.dto.resume.ResumeResponse;
import uz.java.service.ResumeService;

@RestController
@RequestMapping("/resumes")
public class ResumeController {

    private final ResumeService service;
    public ResumeController(ResumeService service) {
        this.service = service;
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getOne(@PathVariable Long id) {
        return ResponseEntity.ok(service.getOne(id));
    }

    @PostMapping
    public ResponseEntity<?> create(@RequestBody @Valid ResumeRequest request) {
        return ResponseEntity.ok(service.create(request));
    }

    @PutMapping
    public ResponseEntity<?> update(@PathVariable Long id, @RequestBody ResumeRequest request) {
        return ResponseEntity.ok(service.update(id, request));
    }
}
