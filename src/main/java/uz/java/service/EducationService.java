package uz.java.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import uz.java.dto.cache.ApiResponse;
import uz.java.dto.cache.CacheDto;
import uz.java.dto.resume.EducationFilter;
import uz.java.dto.resume.EducationRequest;
import uz.java.dto.resume.EducationResponse;
import uz.java.dto.resume.EducationShortResponse;
import uz.java.entity.jobseeker.Education;
import uz.java.exception.GenericNotFoundException;
import uz.java.exception.RedisNotSerializableException;
import uz.java.mapper.EducationMapper;
import uz.java.repository.EducationRepository;
import uz.java.specifications.SearchSpecification;
import uz.java.util.CachePrefix;

import java.util.List;

@Service
@RequiredArgsConstructor
public class EducationService {

    private final EducationRepository educationRepository;
    private final EducationMapper educationMapper;
    private final CacheManagerService cacheManagerService;
    private static final String EXCEPTION_MESSAGE = "education.not.found";

    @Transactional(readOnly = true)
    public EducationResponse getOne(Long id) {
        Object data = cacheManagerService.get(id.toString(), CachePrefix.EDUCATION);
        if (data != null)
            return (EducationResponse) data;

        Education education = educationRepository.findById(id).orElseThrow(
                () -> new GenericNotFoundException(EXCEPTION_MESSAGE)
        );
        EducationResponse response = educationMapper.toResponse(education);
        try {
            cacheManagerService.put(id.toString(), CachePrefix.EDUCATION, response);
        } catch (Exception e) {
            throw new RedisNotSerializableException(e.getMessage());
        }
        return response;
    }
    @Transactional
    public Long create(EducationRequest educationRequest) {
        Education education = educationMapper.toEntity(educationRequest);
        Long id = educationRepository.save(education).getId();
        cacheManagerService.delete(CachePrefix.EDUCATION);
        return id;
    }
    @Transactional
    public Boolean update(Long id, EducationRequest request) {
        Education education = educationRepository.findById(id).orElseThrow(
                () -> new GenericNotFoundException(EXCEPTION_MESSAGE)
        );
        educationMapper.updateFromRequest(request, education);
        educationRepository.save(education);
        cacheManagerService.delete(CachePrefix.EDUCATION);
        return true;
    }

    @Transactional
    public Boolean delete(Long id) {
        Education education = educationRepository.findById(id).orElseThrow(
                () -> new GenericNotFoundException(EXCEPTION_MESSAGE)
        );
        education.makeAsDeleted();
        educationRepository.save(education);
        cacheManagerService.delete(CachePrefix.EDUCATION);
        return true;
    }

    @Transactional(readOnly = true)
    public EducationResponse getByName(String name) {
        Education education = educationRepository.findByName(name);
        return getOne(education.getId());
    }

    @Transactional(readOnly = true)
    public ApiResponse<List<EducationShortResponse>> getAll(EducationFilter filter) {
        Object data = cacheManagerService.get(String.valueOf(filter.hashCode()), CachePrefix.EDUCATION);
        if (data != null) {
            return (ApiResponse<List<EducationShortResponse>>) data;
        }
        List<EducationShortResponse> response = educationRepository.findAllCustom(filter.getName() != null ? filter.getName() : "", SearchSpecification.getPageable(filter.getPage(), filter.getLimit(), filter.getSortBy()));
        try {
            cacheManagerService.put(String.valueOf(filter.hashCode()), CachePrefix.EDUCATION, new ApiResponse<>(response));
        } catch (Exception e) {
            throw new RedisNotSerializableException(e.getMessage());
        }
        return new ApiResponse<>(response);
    }
}

