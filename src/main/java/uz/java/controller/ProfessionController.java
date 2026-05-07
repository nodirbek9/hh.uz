package uz.java.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import uz.java.service.ProfessionService;

@RestController
@RequestMapping("/professions")
public class ProfessionController {

    private final ProfessionService service;

    public ProfessionController(ProfessionService service) {
        this.service = service;
    }

    @GetMapping
    public ResponseEntity<?> getProfessions(@RequestParam Integer page,
                                            @RequestParam Integer limit,
                                            @RequestParam String sortBy) {
        return ResponseEntity.ok(service.getAll(page, limit, sortBy));
    }

    @GetMapping("/{parentId}")
    public ResponseEntity<?> getProfessionByParentId(@PathVariable Long parentId){
        return ResponseEntity.ok(service.getProfessionByParentId(parentId));
    }

}
