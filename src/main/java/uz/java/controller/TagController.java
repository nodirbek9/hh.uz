package uz.java.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import uz.java.dto.tag.TagRequest;
import uz.java.dto.tag.TagResponse;
import uz.java.service.TagService;

import java.util.List;

@RequiredArgsConstructor
@RequestMapping("/tags")
@RestController
public class TagController {

    private final TagService tagService;

    @GetMapping("/{id}")
    public ResponseEntity<TagResponse> getOne(@PathVariable Long id) {
        return ResponseEntity.ok(tagService.getOne(id));
    }

    @PostMapping
    public ResponseEntity<Long> create(@RequestBody TagRequest request){
        return new ResponseEntity<>(tagService.create(request), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Boolean> update(@PathVariable Long id, @RequestBody TagRequest request) {
        return new ResponseEntity<>(tagService.update(id, request), HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<List<TagResponse>> getAll() {
        return ResponseEntity.ok(tagService.getAll());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Boolean> delete(@PathVariable Long id) {
        return ResponseEntity.ok(tagService.delete(id));
    }

    @GetMapping("/get-by-name")
    public ResponseEntity<TagResponse> findByName(@RequestParam String name) {
        return ResponseEntity.ok(tagService.getByName(name));
    }
}
