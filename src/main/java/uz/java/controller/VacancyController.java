package uz.java.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import uz.java.dto.vacancy.VacancyFilter;
import uz.java.dto.vacancy.VacancyRequest;
import uz.java.dto.vacancy.VacancyResponse;
import uz.java.service.VacancyService;

import java.util.List;

@RestController
@RequestMapping("/vacancies")
@RequiredArgsConstructor
public class VacancyController {

    private final VacancyService vacancyService;

    @GetMapping("/{id}")
    public ResponseEntity<VacancyResponse> getOne(@PathVariable Long id) {
        return ResponseEntity.ok(vacancyService.getOne(id));
    }

    @PostMapping
    public ResponseEntity<Long> create(@RequestBody VacancyRequest request) {
        return new ResponseEntity(vacancyService.create(request), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Boolean> update(@PathVariable Long id, @RequestBody VacancyRequest request){
        return ResponseEntity.ok(vacancyService.update(id, request));

    }

    @GetMapping
    public ResponseEntity<List<VacancyResponse>> getAll(@RequestParam Integer page,
                                                        @RequestParam Integer limit,
                                                        @RequestParam String sortBy,
                                                        @RequestParam(required = false) String search,
                                                        @RequestParam(required = false) Integer salaryFrom,
                                                        @RequestParam(required = false) String currencyType,
                                                        @RequestBody(required = false) List<String> employmentType,
                                                        @RequestParam(required = false) List<String> experience,
                                                        @RequestParam(required = false) List<String> workTimeSlot,
                                                        @RequestParam(required = false) List<String> workFormat,
                                                        @RequestParam(required = false) List<Long> companyIndustryId,
                                                        @RequestParam(required = false) List<Long> countryIds,
                                                        @RequestParam(required = false) List<Long> cityIds,
                                                        @RequestParam(required = false) List<String> salaryFrequency,
                                                        @RequestParam(required = false) List<String> vocational,
                                                        @RequestParam(required = false) List<String> workingHours,
                                                        @RequestParam(required = false) List<String> vacancyDisplay){
        return ResponseEntity.ok(vacancyService.getAll(new VacancyFilter(page, limit, sortBy, search,
                salaryFrom, currencyType, employmentType, experience, workTimeSlot, workFormat,
                companyIndustryId, countryIds, cityIds, salaryFrequency, vocational, workingHours, vacancyDisplay)));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Boolean> delete(@PathVariable Long id){
        return ResponseEntity.ok(vacancyService.delete(id));
    }

    @GetMapping("/get-by-name")
    public ResponseEntity<VacancyResponse> findByName(@RequestParam String name){
        return ResponseEntity.ok(vacancyService.getByName(name));
    }
}
