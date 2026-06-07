package uz.java.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import uz.java.service.ProfessionService;

@RestController
@RequestMapping("/professions")
public class ProfessionController {

    private final ProfessionService service;

    public ProfessionController(ProfessionService service) {
        this.service = service;
    }

    @PreAuthorize("hasAnyRole('ADMIN','EMPLOYER','JOB_SEEKER')")
    @GetMapping
    public ResponseEntity<?> getProfessions(@RequestParam Integer page,
                                            @RequestParam Integer limit,
                                            @RequestParam String sortBy) {
        return ResponseEntity.ok(service.getAll(page, limit, sortBy));
    }

    @PreAuthorize("hasAnyRole('ADMIN','EMPLOYER','JOB_SEEKER')")
    @GetMapping("/{parentId}")
    public ResponseEntity<?> getProfessionByParentId(@PathVariable Long parentId){
        return ResponseEntity.ok(service.getProfessionByParentId(parentId));
    }

}
