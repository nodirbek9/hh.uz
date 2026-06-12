package uz.java.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import uz.java.dto.cache.ApiResponse;
import uz.java.dto.cache.CacheDto;
import uz.java.dto.vacancy.VacancyFilter;
import uz.java.dto.vacancy.VacancyRequest;
import uz.java.dto.vacancy.VacancyResponse;
import uz.java.entity.employer.Vacancy;
import uz.java.exception.GenericNotFoundException;
import uz.java.exception.RedisNotSerializableException;
import uz.java.mapper.VacancyMapper;
import uz.java.repository.VacancyRepository;
import uz.java.specifications.SearchSpecification;
import uz.java.specifications.VacancySpecification;
import uz.java.util.CachePrefix;

import java.util.List;

@Service
@RequiredArgsConstructor
public class VacancyService {

    private final VacancyRepository repository;
    private final VacancyMapper mapper;
    private static final String EXCEPTION_MESSAGE = "vacancy.not.found";
    private final CacheManagerService cacheManagerService;

    @Transactional
    public VacancyResponse getOne(Long id) {
        Object data = cacheManagerService.get(id.toString(), CachePrefix.VACANCY);
        if (data != null)
            return (VacancyResponse) data;
        Vacancy vacancy = repository.findById(id).orElseThrow(
                () -> new GenericNotFoundException(EXCEPTION_MESSAGE)
        );
        vacancy.setViewCount(
                vacancy.getViewCount() == null ? 1 : vacancy.getViewCount() + 1
        );
        VacancyResponse vacancyResponse = mapper.toVacancyResponse(vacancy);
        try {
            cacheManagerService.put(id.toString(), CachePrefix.VACANCY, vacancyResponse);
        } catch (Exception e) {
            throw new RedisNotSerializableException(e.getMessage());
        }
        repository.save(vacancy);
        return vacancyResponse;
    }
    @Transactional
    public Long create(VacancyRequest request) {
        Vacancy vacancy = mapper.toVacancy(request);
        Long id = repository.save(vacancy).getId();
        cacheManagerService.delete(CachePrefix.VACANCY);
        return id;
    }
    @Transactional
    public Boolean  update(Long id, VacancyRequest request) {
        Vacancy vacancy = repository.findById(id).orElseThrow(
                () -> new GenericNotFoundException(EXCEPTION_MESSAGE)
        );
        mapper.updateFromRequest(request, vacancy);
        repository.save(vacancy);
        cacheManagerService.delete(CachePrefix.VACANCY);
        return true;
    }

    @Transactional
    public Boolean delete(Long id) {
        Vacancy vacancy = repository.findById(id).orElseThrow(
                () -> new GenericNotFoundException(EXCEPTION_MESSAGE)
        );
        vacancy.makeAsDeleted();
        repository.save(vacancy);
        cacheManagerService.delete(CachePrefix.VACANCY);
        return true;
    }

    @Transactional(readOnly = true)
    public VacancyResponse getByName(String name) {
        Vacancy vacancy = repository.findByName(name);
        return getOne(vacancy.getId());
    }

    @Transactional(readOnly = true)
    public ApiResponse<List<VacancyResponse>> getAll(VacancyFilter filter) {
        Object data = cacheManagerService.get(String.valueOf(filter.hashCode()), CachePrefix.VACANCY);
        if (data != null) {
            return (ApiResponse<List<VacancyResponse>>) data;
        }
        VacancySpecification spec = new VacancySpecification(filter);
        List<VacancyResponse> response = repository.findAll(spec, SearchSpecification.getPageable(filter.getPage(),
                filter.getLimit(), filter.getSortBy())).stream()
                .filter(vacancy -> !vacancy.isDeleted())
                .map(mapper::toVacancyResponse).toList();
        try {
            cacheManagerService.put(String.valueOf(filter.hashCode()), CachePrefix.VACANCY, new ApiResponse<>(response));
        } catch (Exception e) {
            throw new RedisNotSerializableException(e.getMessage());
        }
        return new ApiResponse<>(response);
    }
}
