package uz.java.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import uz.java.entity.application.VacancyApplication;

import java.util.List;

public interface VacancyApplicationRepository extends JpaRepository<VacancyApplication, Long> {

    @Query("SELECT v FROM VacancyApplication v WHERE v.user.id = ?1 and v.deletedAt is null")
    List<VacancyApplication> findByUserId(Long userId);

    @Query("select a from VacancyApplication a where a.vacancy.id = ?1 and a.deletedAt is null")
    List<VacancyApplication> findByVacancyId(Long vacancyId);

    boolean existsByVacancyIdAndUserId(Long vacancyId, Long userId);
}
