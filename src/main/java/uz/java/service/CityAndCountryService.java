package uz.java.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import uz.java.entity.address.CityAndCountry;
import uz.java.repository.CityAndCountryRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CityAndCountryService {

    private final CityAndCountryRepository repository;

    @Transactional(readOnly = true)
    public List<CityAndCountry> getCountries() {
        return repository.findByParentIdIsNull();
    }

    @Transactional(readOnly = true)
    public List<CityAndCountry> getCities(Long countryId) {
        return repository.findByParentId(countryId);
    }
}
