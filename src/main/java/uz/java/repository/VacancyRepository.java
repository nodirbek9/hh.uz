package uz.java.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import uz.java.entity.employer.Vacancy;

public interface VacancyRepository extends JpaRepository<Vacancy, Long> {
    Vacancy findByName(String name);
}
