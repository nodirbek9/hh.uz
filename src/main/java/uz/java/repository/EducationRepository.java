package uz.java.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import uz.java.entity.jobseeker.Education;

import java.util.List;

public interface EducationRepository extends JpaRepository<Education, Long>, JpaSpecificationExecutor<Education> {

    @Query("select e from Education e where e.name=?1")
    Education findByName(String name);

    @Query("select up from UserProfile up where up.id=?1")
    List<Education> findByUserProfileId(Long userProfileId);
}
