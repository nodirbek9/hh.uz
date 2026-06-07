package uz.java.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import uz.java.entity.saved.SavedResume;

import java.util.List;

public interface SavedResumeRepository extends JpaRepository<SavedResume, Long> {

    @Query("select s from SavedResume s where s.user.id = ?1 and s.deletedAt is null")
    List<SavedResume> findByUserId(Long userId);

    boolean existsByResumeIdAndUserId(Long resumeId, Long userId);
}
