package uz.java.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import uz.java.dto.resume.EducationFilter;
import uz.java.dto.resume.EducationRequest;
import uz.java.dto.resume.EducationResponse;
import uz.java.dto.resume.EducationShortResponse;
import uz.java.entity.jobseeker.Education;
import uz.java.exception.GenericNotFoundException;
import uz.java.mapper.EducationMapper;
import uz.java.repository.EducationRepository;
import uz.java.specifications.SearchSpecification;

import java.util.List;

@Service
@RequiredArgsConstructor
public class EducationService {

    private final EducationRepository educationRepository;
    private final EducationMapper educationMapper;
    private static final String EXCEPTION_MESSAGE = "education.not.found";

    @Transactional(readOnly = true)
    public EducationResponse getOne(Long id) {
        Education education = educationRepository.findById(id).orElseThrow(
                () -> new GenericNotFoundException(EXCEPTION_MESSAGE)
        );
        return educationMapper.toResponse(education);
    }

    public Long create(EducationRequest educationRequest) {
        Education education = educationMapper.toEntity(educationRequest);
        return educationRepository.save(education).getId();
    }

    public Boolean update(Long id, EducationRequest request) {
        Education education = educationRepository.findById(id).orElseThrow(
                () -> new GenericNotFoundException(EXCEPTION_MESSAGE)
        );
        educationMapper.updateFromRequest(request, education);
        educationRepository.save(education);
        return true;
    }

    @Transactional
    public Boolean delete(Long id) {
        Education education = educationRepository.findById(id).orElseThrow(
                () -> new GenericNotFoundException(EXCEPTION_MESSAGE)
        );
        education.makeAsDeleted();
        educationRepository.save(education);
        return true;
    }

    @Transactional(readOnly = true)
    public EducationResponse getByName(String name) {
        Education education = educationRepository.findByName(name);
        return getOne(education.getId());
    }

    @Transactional(readOnly = true)
    public List<EducationShortResponse> getAll(EducationFilter filter) {
        return educationRepository.findAllCustom(filter.getName() != null ? filter.getName() : "", SearchSpecification.getPageable(filter.getPage(), filter.getLimit(), filter.getSortBy()));
    }
}

