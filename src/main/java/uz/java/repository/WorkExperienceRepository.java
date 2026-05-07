package uz.java.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import uz.java.entity.jobseeker.WorkExperience;

import java.util.List;

public interface WorkExperienceRepository extends JpaRepository<WorkExperience, Long>, JpaSpecificationExecutor<WorkExperience> {

    @Query("select work from WorkExperience work where work.userProfile.id=?1")
    List<WorkExperience> findByResumeId(Long id);

    @Query("select work from WorkExperience work where work.userProfile.id=?1")
    List<WorkExperience> findByUserProfileId(Long userProfileId);
}
