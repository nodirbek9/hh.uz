package uz.java.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import uz.java.entity.jobseeker.WorkExperience;

import java.util.List;

public interface WorkExperienceRepository extends JpaRepository<WorkExperience, Long>, JpaSpecificationExecutor<WorkExperience> {

    @Query("select w from WorkExperience w where w.resume.id = ?1 and w.deletedAt is null")
    List<WorkExperience> findByResumeId(Long resumeId);

    @Query("select w from WorkExperience w where w.userProfile.id = ?1 and w.deletedAt is null")
    List<WorkExperience> findByUserProfileId(Long userProfileId);
}
