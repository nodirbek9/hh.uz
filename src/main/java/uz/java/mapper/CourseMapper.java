package uz.java.mapper;

import org.mapstruct.*;
import uz.java.dto.resume.CourseRequest;
import uz.java.dto.resume.CourseResponse;
import uz.java.entity.jobseeker.Course;

@Mapper(componentModel = "spring")
public interface CourseMapper {

    @Mapping(source = "resume.id", target = "resumeId")
    CourseResponse toResponse(Course course);

    @Mapping(source = "resumeId", target = "resume.id")
    Course toCourse(CourseRequest request);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateFromRequest(CourseRequest request, @MappingTarget Course course);
}
