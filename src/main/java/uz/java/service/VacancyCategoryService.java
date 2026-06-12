package uz.java.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import uz.java.dto.cache.ApiResponse;
import uz.java.dto.cache.CacheDto;
import uz.java.dto.vacancy.VacancyCategoryRequest;
import uz.java.dto.vacancy.VacancyCategoryResponse;
import uz.java.entity.employer.Specializations;
import uz.java.exception.GenericNotFoundException;
import uz.java.exception.RedisNotSerializableException;
import uz.java.mapper.VacancyCategoryMapper;
import uz.java.repository.VacancyCategoryRepository;
import uz.java.util.CachePrefix;

import java.util.List;

@Service
@RequiredArgsConstructor
public class VacancyCategoryService {

    private final VacancyCategoryRepository repository;
    private final VacancyCategoryMapper mapper;
    private final CacheManagerService cacheManagerService;

    private static final String EXCEPTION_MESSAGE = "specializations.not.found";

    @Transactional(readOnly = true)
    public VacancyCategoryResponse getOne(Long id) {
        Object data = cacheManagerService.get(id.toString(), CachePrefix.VACANCY_CATEGORY);
        if (data != null)
            return (VacancyCategoryResponse) data;

        Specializations category = repository.findById(id).orElseThrow(
                () -> new GenericNotFoundException(EXCEPTION_MESSAGE)
        );
        VacancyCategoryResponse response = mapper.toVacancyCategoryResponse(category);
        try {
            cacheManagerService.put(id.toString(), CachePrefix.VACANCY_CATEGORY, response);
        } catch (Exception e) {
            throw new RedisNotSerializableException(e.getMessage());
        }
        return response;
    }

    public Long create(VacancyCategoryRequest request) {
        Long id = repository.save(mapper.toVacancyCategory(request)).getId();
        cacheManagerService.delete(CachePrefix.VACANCY_CATEGORY);
        return id;
    }

    public Boolean update(Long id, VacancyCategoryRequest request) {
        Specializations oldCategory = repository.findById(id).orElseThrow(
                () -> new GenericNotFoundException(EXCEPTION_MESSAGE)
        );
        mapper.updateVacancyCategory(request, oldCategory);
        repository.save(oldCategory);
        cacheManagerService.delete(CachePrefix.VACANCY_CATEGORY);
        return true;
    }

    @Transactional(readOnly = true)
    public ApiResponse<List<VacancyCategoryResponse>> getAll() {
        Object data = cacheManagerService.get("all", CachePrefix.VACANCY_CATEGORY);
        if (data != null) {
            return (ApiResponse<List<VacancyCategoryResponse>>) data;
        }
        List<VacancyCategoryResponse> response = repository.findAll().stream().
                filter(c -> !c.isDeleted()).map(mapper::toVacancyCategoryResponse).toList();
        try {
            cacheManagerService.put("all", CachePrefix.VACANCY_CATEGORY, new ApiResponse<>(response));
        } catch (Exception e) {
            throw new RedisNotSerializableException(e.getMessage());
        }
        return new ApiResponse<>(response);
    }

    @Transactional
    public Boolean delete(Long id) {
        Specializations category = repository.findById(id).orElseThrow(
                () -> new GenericNotFoundException(EXCEPTION_MESSAGE)
        );

        category.makeAsDeleted();
        repository.save(category);
        cacheManagerService.delete(CachePrefix.VACANCY_CATEGORY);
        return true;
    }

    @Transactional(readOnly = true)
    public VacancyCategoryResponse getByName(String name) {
        Specializations category = repository.findByNameCustom(name);
        return getOne(category.getId());
    }
}
