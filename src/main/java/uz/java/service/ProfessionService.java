package uz.java.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import uz.java.dto.cache.ApiResponse;
import uz.java.dto.resume.ProfessionResponse;
import uz.java.entity.employer.Profession;
import uz.java.exception.RedisNotSerializableException;
import uz.java.mapper.ProfessionMapper;
import uz.java.repository.ProfessionRepository;
import uz.java.specifications.SearchSpecification;
import uz.java.util.CachePrefix;

import java.util.List;

@Service
public class ProfessionService {
    private final ProfessionRepository professionRepository;
    private final ProfessionMapper mapper;
    private final CacheManagerService cacheManagerService;

    public ProfessionService(ProfessionRepository professionRepository, ProfessionMapper mapper, CacheManagerService cacheManagerService) {
        this.professionRepository = professionRepository;
        this.mapper = mapper;
        this.cacheManagerService = cacheManagerService;
    }

    @Transactional(readOnly = true)
    public ApiResponse<List<ProfessionResponse>> getAll(Integer page, Integer limit, String sortBy) {
        String cacheKey = String.valueOf((page + "_" + limit + "_" + sortBy).hashCode());
        Object data = cacheManagerService.get(cacheKey, CachePrefix.PROFESSION);
        if (data != null) {
            return (ApiResponse<List<ProfessionResponse>>) data;
        }
        List<ProfessionResponse> response = professionRepository.findAll(SearchSpecification.getPageable(
                page, limit, sortBy)).stream().map(mapper::toResponse).toList();
        try {
            cacheManagerService.put(cacheKey, CachePrefix.PROFESSION, new ApiResponse<>(response));
        } catch (Exception e) {
            throw new RedisNotSerializableException(e.getMessage());
        }
        return new ApiResponse<>(response);
    }
    @Transactional(readOnly = true)
    public ApiResponse<List<ProfessionResponse>> getProfessionByParentId(Long parentId) {
        Object data = cacheManagerService.get("parent_" + parentId, CachePrefix.PROFESSION);
        if (data != null) {
            return (ApiResponse<List<ProfessionResponse>>) data;
        }
        List<Profession> list = professionRepository.findByParentId(parentId);
        List<ProfessionResponse> response = list.stream().map(mapper::toResponse).toList();
        try {
            cacheManagerService.put("parent_" + parentId, CachePrefix.PROFESSION, new ApiResponse<>(response));
        } catch (Exception e) {
            throw new RedisNotSerializableException(e.getMessage());
        }
        return new ApiResponse<>(response);
    }
}
