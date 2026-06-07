package uz.java.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import uz.java.entity.address.CityAndCountry;

import java.util.List;

public interface CityAndCountryRepository extends JpaRepository<CityAndCountry, Long> {

    List<CityAndCountry> findByParentIdIsNull();

    List<CityAndCountry> findByParentId(Long parentId);
}
