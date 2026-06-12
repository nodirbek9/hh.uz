package uz.java.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import uz.java.dto.cache.ApiResponse;
import uz.java.dto.cache.CacheDto;
import uz.java.dto.resume.SkillRequest;
import uz.java.dto.resume.SkillResponse;
import uz.java.entity.jobseeker.Skill;
import uz.java.exception.GenericNotFoundException;
import uz.java.exception.RedisNotSerializableException;
import uz.java.mapper.SkillMapper;
import uz.java.repository.SkillRepository;
import uz.java.util.CachePrefix;

import java.util.List;

@Service
@RequiredArgsConstructor
public class SkillService {

    private final SkillRepository repository;
    private final SkillMapper mapper;
    private final CacheManagerService cacheManagerService;
    private static final String EXCEPTION_MESSAGE = "skill.not.found";

    @Transactional(readOnly = true)
    public SkillResponse getOne(Long id) {
        Object data = cacheManagerService.get(id.toString(), CachePrefix.SKILL);
        if (data != null)
            return (SkillResponse) data;

        Skill skill = repository.findById(id).orElseThrow(
                () -> new GenericNotFoundException(EXCEPTION_MESSAGE)
        );
        SkillResponse response = mapper.toResponse(skill);
        try {
            cacheManagerService.put(id.toString(), CachePrefix.SKILL, response);
        } catch (Exception e) {
            throw new RedisNotSerializableException(e.getMessage());
        }
        return response;
    }
    @Transactional
    public Long create(SkillRequest request) {
        Skill skill = mapper.toEntity(request);
        Long id = repository.save(skill).getId();
        cacheManagerService.delete(CachePrefix.SKILL);
        return id;
    }
    @Transactional
    public Boolean update(Long id, SkillRequest request) {
        Skill oldskill = repository.findById(id).orElseThrow(
                () -> new GenericNotFoundException(EXCEPTION_MESSAGE)
        );
        mapper.updateFromRequest(request, oldskill);
        repository.save(oldskill);
        cacheManagerService.delete(CachePrefix.SKILL);
        return true;
    }

    @Transactional(readOnly = true)
    public ApiResponse<List<SkillResponse>> getAll(String search) {
        Object data = cacheManagerService.get(String.valueOf(search.hashCode()), CachePrefix.SKILL);
        if (data != null) {
            return (ApiResponse<List<SkillResponse>>) data;
        }
        List<SkillResponse> response = repository.findAllCustom(search);
        try {
            cacheManagerService.put(String.valueOf(search.hashCode()), CachePrefix.SKILL, new ApiResponse<>(response));
        } catch (Exception e) {
            throw new RedisNotSerializableException(e.getMessage());
        }
        return new ApiResponse<>(response);
    }
    @Transactional
    public Boolean delete(Long id) {
        Skill skill = repository.findById(id).orElseThrow(
                () -> new GenericNotFoundException(EXCEPTION_MESSAGE)
        );

        skill.makeAsDeleted();
        repository.save(skill);
        cacheManagerService.delete(CachePrefix.SKILL);
        return true;
    }

    @Transactional(readOnly = true)
    public SkillResponse getByName(String name) {
        Skill skill = repository.findByNameCustom(name);
        return getOne(skill.getId());
    }
}
