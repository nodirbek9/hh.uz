package uz.java.mapper;

import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;
import uz.java.dto.resume.ResumeResponse;
import uz.java.dto.resume.SkillRequest;
import uz.java.entity.jobseeker.Skill;

@Mapper(componentModel = "spring")
public interface SkillMapper {
    ResumeResponse.SkillResponse toResponse(Skill skill);

    Skill toEntity(SkillRequest request);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateFromRequest(SkillRequest request, @MappingTarget Skill oldskill);
}