package uz.java.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import uz.java.entity.employer.VacancyCategory;

public interface VacancyCategoryRepository extends JpaRepository<VacancyCategory, Long> {

    @Query("select v from VacancyCategory v where v.name=?1")
    VacancyCategory findVacancyCategoriesByName(String name);
}
