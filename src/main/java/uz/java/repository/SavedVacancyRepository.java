package uz.java.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import uz.java.entity.saved.SavedVacancy;

import java.util.List;
import java.util.Optional;

public interface SavedVacancyRepository extends JpaRepository<SavedVacancy, Long> {

    @Query("select s from SavedVacancy s where s.user.id = ?1 and s.deletedAt is null")
    List<SavedVacancy> findByUserId(Long userId);

    boolean existsByVacancyIdAndUserId(Long vacancyId, Long userId);

    @Query("select s from SavedVacancy s where s.vacancy.id = ?1 and s.user.id = ?2")
    Optional<SavedVacancy> findByVacancyIdAndUserId(Long vacancyId, Long userId);
}
