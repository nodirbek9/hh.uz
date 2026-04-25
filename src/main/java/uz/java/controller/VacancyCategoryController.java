package uz.java.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import uz.java.dto.tag.TagRequest;
import uz.java.dto.tag.TagResponse;
import uz.java.dto.vacancy.VacancyCategoryRequest;
import uz.java.dto.vacancy.VacancyCategoryResponse;
import uz.java.entity.employer.VacancyCategory;
import uz.java.service.TagService;
import uz.java.service.VacancyCategoryService;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/vacancyCategories")
public class VacancyCategoryController {

    private final VacancyCategoryService vacancyCategoryService;

    @GetMapping("/{id}")
    public ResponseEntity<VacancyCategoryResponse> getOne(@PathVariable Long id) {
        return ResponseEntity.ok(vacancyCategoryService.getOne(id));
    }

    @PostMapping
    public ResponseEntity<Long> create(@RequestBody VacancyCategoryRequest request){
        return new ResponseEntity<>(vacancyCategoryService.create(request), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Boolean> update(@PathVariable Long id, @RequestBody VacancyCategoryRequest request) {
        return new ResponseEntity<>(vacancyCategoryService.update(id, request), HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<List<VacancyCategoryResponse>> getAll() {
        return ResponseEntity.ok(vacancyCategoryService.getAll());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Boolean> delete(@PathVariable Long id) {
        return ResponseEntity.ok(vacancyCategoryService.delete(id));
    }

    @GetMapping("/get-by-name")
    public ResponseEntity<VacancyCategoryResponse> findByName(@RequestParam String name) {
        return ResponseEntity.ok(vacancyCategoryService.getByName(name));
    }
}
