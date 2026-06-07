package uz.java.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import uz.java.dto.resume.SkillResponse;
import uz.java.entity.jobseeker.Skill;

import java.util.List;

public interface SkillRepository extends JpaRepository<Skill, Long>, JpaSpecificationExecutor<Skill> {

    @Query("select s from Skill s where s.name=:name")
    Skill findByNameCustom(String name);

    @Query("select new uz.java.dto.resume.SkillResponse(s.id, s.name, s.code)" +
            "from Skill s where lower(s.name) ilike lower(concat('%', :name, '%')) and s.deletedAt is null")
    List<SkillResponse> findAllCustom(@Param("name") String name);
}
