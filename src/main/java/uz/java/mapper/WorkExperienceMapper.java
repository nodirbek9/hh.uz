package uz.java.mapper;

import  org.mapstruct.Mapper;
import uz.java.dto.resume.ResumeRequest;
import uz.java.dto.user.WorkExperienceResponse;
import uz.java.entity.jobseeker.WorkExperience;

@Mapper(componentModel = "spring")
public interface WorkExperienceMapper {
    WorkExperience toEntity(ResumeRequest.WorkExperienceDto workExperienceDto);

    WorkExperienceResponse toResponse(WorkExperience workExperience);

}
