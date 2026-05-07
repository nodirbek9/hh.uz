package uz.java.mapper;

import org.mapstruct.*;
import uz.java.dto.resume.ResumeRequest;
import uz.java.dto.resume.ResumeResponse;
import uz.java.entity.jobseeker.Resume;

@Mapper(componentModel = "spring")
public interface ResumeMapper {
    @Mapping(source = "profession.name", target = "profession")
    ResumeResponse toResponse(Resume resume);

    Resume toEntity(ResumeRequest request);

    @Mapping(target = "userProfile", ignore = true)
    @Mapping(target = "certificateList", ignore = true)
    @Mapping(target = "skills", ignore = true)
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateFromRequest(ResumeRequest request, @MappingTarget Resume resume);
}
