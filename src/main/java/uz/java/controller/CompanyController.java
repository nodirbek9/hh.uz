package uz.java.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
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
    public ResponseEntity<CompanyResponse> getOne(@PathVariable Long id) {
        return ResponseEntity.ok(companyService.getOne(id));
    }
    // ResponseEntity class bu response ni wrap qilib uni Front tarafga statusCode, status, body si ga ajratib yuboradi

    @PostMapping
    public ResponseEntity<Long> create(@RequestBody @Valid CompanyRequest request) {
        return new ResponseEntity<>(companyService.create(request), HttpStatus.CREATED);
    }
    @PutMapping("/{id}")
    public ResponseEntity<Boolean> update(@PathVariable Long id, @RequestBody CompanyRequest request){
        return new ResponseEntity<>(companyService.update(id, request), HttpStatus.OK);
    }
    @GetMapping
    public ResponseEntity<List<CompanyResponse>> getAll(){
        return ResponseEntity.ok(companyService.getAll());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Boolean> delete(@PathVariable Long id){
        return ResponseEntity.ok(companyService.delete(id));
    }

    @GetMapping("/get-by-name")
    public ResponseEntity<CompanyResponse> findByName(@RequestParam String name){
        return ResponseEntity.ok(companyService.getByName(name));
    }

}
