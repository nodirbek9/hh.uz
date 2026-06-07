package uz.java.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import uz.java.dto.company.CompanyFilter;
import uz.java.dto.company.CompanyRequest;
import uz.java.dto.company.CompanyResponse;
import uz.java.service.CompanyService;

import java.util.List;

// REST api da kotaramiz, methodlari: POST, GET, PUT, DELETE, PATCH
//REST -->>> Frontdan 1 ta request keladi backend ga front uni javobi kelmaguncha boshqa request yubormaydi

// bu anotatsiya 2 ta anotasiyaga teng( @ResponsBody + @Controller)
// @Controller anotatsiya faqta model, view request lani handle qiladi bu Spring MVC da ishlatiladi
// ResponsBody bu request body si html, model keladi shulani handle qilishi uchun
@RestController  // koproq JSON, XML, text, file turdagi data larni handle qiladi
@RequestMapping("/companies") // hamma endpointlani boshi, bu domain dan keyin turadigan bosh url
@RequiredArgsConstructor
public class CompanyController {

//        DI(Dependency Injection) bu ikkita bean class lar bir birini ichiga chaqirib ishlatish
//        4 xil usulda yoziladi farqi sintaksisda:
//        1. Field Injection(@Autowired anotatsiya ishlatiladi)
//        2. Constructor-based injection(bu final keyword qoyiladi va constructor yaratib beradi)
//        3. Setter-based Injection (setter method yaratiladi);
//        4. @RequiredArgsConstructor orqali injection(bonus sifatida)

    private final CompanyService companyService;

    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN','JOB_SEEKER','EMPLOYER')")
    public ResponseEntity<CompanyResponse> getOne(@PathVariable Long id) {
        return ResponseEntity.ok(companyService.getOne(id));
    }
    // ResponseEntity class bu response ni wrap qilib uni Front tarafga statusCode, status, body si ga ajratib yuboradi

    @PostMapping
    @PreAuthorize("hasAnyRole('ADMIN','EMPLOYER')")
    public ResponseEntity<Long> create(@RequestBody @Valid CompanyRequest request) {
        return new ResponseEntity<>(companyService.create(request), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN','EMPLOYER')")
    public ResponseEntity<Boolean> update(@PathVariable Long id, @RequestBody CompanyRequest request) {
        return new ResponseEntity<>(companyService.update(id, request), HttpStatus.OK);
    }

    //    Long id;
//    String name;
//    Long ownerId;
//    String description;
//    String logo;
//    String country;
//    String city;
//    String phone;
//    String email;
//    List<String> imageUrls;
    @GetMapping
    @PreAuthorize("hasAnyRole('ADMIN','JOB_SEEKER','EMPLOYER')")
    public ResponseEntity<List<CompanyResponse>> getAll(@RequestParam Integer page,
                                                        @RequestParam Integer limit,
                                                        @RequestParam String sortBy,
                                                        @RequestParam(required = false) String name,
                                                        @RequestParam(required = false) String description,
                                                        @RequestParam(required = false) String country,
                                                        @RequestParam(required = false) String city,
                                                        @RequestParam(required = false) String phone,
                                                        @RequestParam(required = false) String email
    ) {
        return ResponseEntity.ok(companyService.getAll(
                new CompanyFilter(page, limit, sortBy, name, description, country, city, phone, email)
        ));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN','EMPLOYER')")
    public ResponseEntity<Boolean> delete(@PathVariable Long id) {
        return ResponseEntity.ok(companyService.delete(id));
    }

    @GetMapping("/get-by-name")
    @PreAuthorize("hasAnyRole('ADMIN','EMPLOYER')")
    public ResponseEntity<CompanyResponse> findByName(@RequestParam String name) {
        return ResponseEntity.ok(companyService.getByName(name));
    }
}
