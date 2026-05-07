package uz.java.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import uz.java.entity.jobseeker.Language;

import java.util.List;

public interface LanguageRepository extends JpaRepository<Language, Long>, JpaSpecificationExecutor<Language> {

    @Query("select l from Language l where l.userProfile.id=?1")
    List<Language> findByUserProfileId(Long id);

}
