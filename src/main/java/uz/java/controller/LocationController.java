package uz.java.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import uz.java.entity.address.CityAndCountry;
import uz.java.service.CityAndCountryService;

import java.util.List;

@RestController
@RequestMapping("/locations")
@RequiredArgsConstructor
public class LocationController {

    private final CityAndCountryService service;

    @GetMapping("/countries")
    public ResponseEntity<List<CityAndCountry>> getCountries() {
        return ResponseEntity.ok(service.getCountries());
    }

    @GetMapping("/cities")
    public ResponseEntity<List<CityAndCountry>> getCities(
            @RequestParam Long countryId) {
        return ResponseEntity.ok(service.getCities(countryId));
    }

}
