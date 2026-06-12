package uz.java.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import uz.java.dto.cache.ApiResponse;
import uz.java.dto.cache.CacheDto;
import uz.java.dto.resume.CourseFilter;
import uz.java.dto.resume.CourseRequest;
import uz.java.dto.resume.CourseResponse;
import uz.java.entity.jobseeker.Course;
import uz.java.exception.GenericNotFoundException;
import uz.java.exception.RedisNotSerializableException;
import uz.java.mapper.CourseMapper;
import uz.java.repository.CourseRepository;
import uz.java.specifications.CourseSpecification;
import uz.java.specifications.SearchSpecification;
import uz.java.util.CachePrefix;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CourseService {

    private final CourseRepository courseRepository;
    private final CourseMapper courseMapper;
    private final CacheManagerService cacheManagerService;

    @Transactional(readOnly = true)
    public CourseResponse getOne(Long id) {
        Object data = cacheManagerService.get(id.toString(), CachePrefix.COURSE);
        if (data != null)
            return (CourseResponse) data;

        Course course = courseRepository.findById(id).orElseThrow(
                () -> new GenericNotFoundException("course.not.found")
        );
        CourseResponse response = courseMapper.toResponse(course);
        try {
            cacheManagerService.put(id.toString(), CachePrefix.COURSE, response);
        } catch (Exception e) {
            throw new RedisNotSerializableException(e.getMessage());
        }
        return response;
    }

    @Transactional
    public Long create(CourseRequest request) {
        Course course = courseMapper.toCourse(request);
        Long id = courseRepository.save(course).getId();
        cacheManagerService.delete(CachePrefix.COURSE);
        return id;
    }

    @Transactional
    public Boolean update(Long id, CourseRequest request) {
        Course course = courseRepository.findById(id).orElseThrow(
                () -> new GenericNotFoundException("course.not.found")
        );
        courseMapper.updateFromRequest(request, course);
        courseRepository.save(course);
        cacheManagerService.delete(CachePrefix.COURSE);
        return true;
    }

    @Transactional(readOnly = true)
    public ApiResponse<List<CourseResponse>> getAll(CourseFilter filter) {
        Object data = cacheManagerService.get(String.valueOf(filter.hashCode()), CachePrefix.COURSE);
        if (data != null) {
            return (ApiResponse<List<CourseResponse>>) data;
        }
        CourseSpecification spec = new CourseSpecification(filter);
        List<CourseResponse> response = courseRepository.findAll(spec, SearchSpecification.getPageable(filter.getPage(),
                        filter.getLimit(), filter.getSortBy())).stream()
                .filter(c -> !c.isDeleted())
                .map(courseMapper::toResponse).toList();
        try {
            cacheManagerService.put(String.valueOf(filter.hashCode()), CachePrefix.COURSE, new ApiResponse<>(response));
        } catch (Exception e) {
            throw new RedisNotSerializableException(e.getMessage());
        }
        return new ApiResponse<>(response);
    }

    @Transactional
    public Boolean delete(Long id) {
        Course course = courseRepository.findById(id).orElseThrow(
                () -> new GenericNotFoundException("course.not.found")
        );
        course.makeAsDeleted();
        courseRepository.save(course);
        cacheManagerService.delete(CachePrefix.COURSE);
        return true;
    }
}
