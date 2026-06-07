package uz.java.repository;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import uz.java.dto.resume.EducationShortResponse;
import uz.java.entity.jobseeker.Education;

import java.util.List;

public interface EducationRepository extends JpaRepository<Education, Long>, JpaSpecificationExecutor<Education> {

    @Query("select e from Education e where e.name=?1")
    Education findByName(String name);

    @Query("select e from Education e where e.userProfile.id = ?1 and e.deletedAt is null")
    List<Education> findByUserProfileId(Long userProfileId);

    @Query("select new uz.java.dto.resume.EducationShortResponse(e.id, e.name, e.degree) from Education e where e.name ilike '%' || :name || '%' and e.deletedAt is null ")
    List<EducationShortResponse> findAllCustom(String name, Pageable pageable);
}
