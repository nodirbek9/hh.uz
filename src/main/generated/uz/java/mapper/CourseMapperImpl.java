package uz.java.mapper;

import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;
import uz.java.dto.resume.CourseRequest;
import uz.java.dto.resume.CourseResponse;
import uz.java.entity.jobseeker.Course;
import uz.java.entity.jobseeker.Resume;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2026-06-10T21:35:54+0300",
    comments = "version: 1.5.5.Final, compiler: javac, environment: Java 21.0.8 (Amazon.com Inc.)"
)
@Component
public class CourseMapperImpl implements CourseMapper {

    @Override
    public CourseResponse toResponse(Course course) {
        if ( course == null ) {
            return null;
        }

        CourseResponse courseResponse = new CourseResponse();

        courseResponse.setResumeId( courseResumeId( course ) );
        courseResponse.setId( course.getId() );
        courseResponse.setName( course.getName() );
        courseResponse.setOrganization( course.getOrganization() );
        courseResponse.setSpecialisation( course.getSpecialisation() );
        courseResponse.setGraduationYear( course.getGraduationYear() );

        return courseResponse;
    }

    @Override
    public Course toCourse(CourseRequest request) {
        if ( request == null ) {
            return null;
        }

        Course course = new Course();

        course.setResume( courseRequestToResume( request ) );
        course.setName( request.getName() );
        course.setOrganization( request.getOrganization() );
        course.setSpecialisation( request.getSpecialisation() );
        course.setGraduationYear( request.getGraduationYear() );

        return course;
    }

    @Override
    public void updateFromRequest(CourseRequest request, Course course) {
        if ( request == null ) {
            return;
        }

        if ( request.getName() != null ) {
            course.setName( request.getName() );
        }
        if ( request.getOrganization() != null ) {
            course.setOrganization( request.getOrganization() );
        }
        if ( request.getSpecialisation() != null ) {
            course.setSpecialisation( request.getSpecialisation() );
        }
        if ( request.getGraduationYear() != null ) {
            course.setGraduationYear( request.getGraduationYear() );
        }
    }

    private Long courseResumeId(Course course) {
        if ( course == null ) {
            return null;
        }
        Resume resume = course.getResume();
        if ( resume == null ) {
            return null;
        }
        Long id = resume.getId();
        if ( id == null ) {
            return null;
        }
        return id;
    }

    protected Resume courseRequestToResume(CourseRequest courseRequest) {
        if ( courseRequest == null ) {
            return null;
        }

        Resume resume = new Resume();

        resume.setId( courseRequest.getResumeId() );

        return resume;
    }
}
