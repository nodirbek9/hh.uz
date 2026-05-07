package uz.java.mapper;

import org.mapstruct.*;
import uz.java.dto.resume.EducationRequest;
import uz.java.dto.resume.EducationResponse;
import uz.java.entity.jobseeker.Education;

@Mapper(componentModel = "spring")
public interface EducationMapper {

    @Mapping(source = "userProfile.id", target = "userProfileId")
    EducationResponse toResponse(Education education);

    @Mapping(source = "userProfileId", target = "userProfile.id")
    Education toEntity(EducationRequest educationRequest);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateFromRequest(EducationRequest request, @MappingTarget Education education);
}
