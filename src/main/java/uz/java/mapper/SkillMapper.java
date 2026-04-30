package uz.java.mapper;

import org.mapstruct.Mapper;
import uz.java.dto.resume.ResumeResponse;
import uz.java.entity.jobseeker.Skill;

@Mapper(componentModel = "spring")
public interface SkillMapper {
    ResumeResponse.SkillResponse toResponse(Skill skill);
}
