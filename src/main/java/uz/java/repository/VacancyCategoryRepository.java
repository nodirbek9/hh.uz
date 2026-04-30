package uz.java.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import uz.java.entity.employer.Specializations;
import uz.java.entity.employer.VacancyCategory;

public interface VacancyCategoryRepository extends JpaRepository<Specializations, Long> {

    @Query("select v from Specializations v where v.name=:name")
    Specializations findByNameCustom(String name);
}
