package uz.java.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import uz.java.dto.resume.CourseRequest;
import uz.java.dto.resume.CourseResponse;
import uz.java.entity.jobseeker.Course;
import uz.java.exception.GenericNotFoundException;
import uz.java.mapper.CourseMapper;
import uz.java.repository.CourseRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CourseService {

    private final CourseRepository courseRepository;
    private final CourseMapper courseMapper;

    public CourseResponse getOne(Long id) {
        Course course = courseRepository.findById(id).orElseThrow(
                () -> new GenericNotFoundException("course.not.found")
        );
        return courseMapper.toResponse(course);
    }


    public Long create(CourseRequest request) {
        Course course = courseMapper.toCourse(request);
        Course save = courseRepository.save(course);
        return save.getId();
    }


    public Boolean update(Long id, CourseRequest request) {
        Course course = courseRepository.findById(id).orElseThrow(
                () -> new GenericNotFoundException("course.not.found")
        );
        courseMapper.updateFromRequest(request, course);
        courseRepository.save(course);
        return true;
    }

    public List<CourseResponse> getAll() {
        return courseRepository.findAll().stream()
                .filter(c -> !c.isDeleted())
                .map(courseMapper::toResponse).toList();
    }

    public Boolean delete(Long id) {
        Course course = courseRepository.findById(id).orElseThrow(
                () -> new GenericNotFoundException("course.not.found")
        );
        course.makeAsDeleted();
        courseRepository.save(course);
        return true;
    }
}
