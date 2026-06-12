package uz.java.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import uz.java.dto.cache.ApiResponse;
import uz.java.dto.cache.CacheDto;
import uz.java.dto.tag.TagRequest;
import uz.java.dto.tag.TagResponse;
import uz.java.entity.employer.Tag;
import uz.java.exception.GenericNotFoundException;
import uz.java.exception.RedisNotSerializableException;
import uz.java.mapper.TagMapper;
import uz.java.repository.TagRepository;
import uz.java.util.CachePrefix;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class TagService {

    private final TagRepository tagRepository;
    private final TagMapper tagMapper;
    private final CacheManagerService cacheManagerService;
    private static final String EXCEPTION_MESSAGE = "tag.not.found";

    @Transactional(readOnly = true)
    public TagResponse getOne(Long id) {
        Object data = cacheManagerService.get(id.toString(), CachePrefix.TAG);
        if (data != null)
            return (TagResponse) data;

        Tag tag = tagRepository.findById(id).orElseThrow(
                () -> new GenericNotFoundException(EXCEPTION_MESSAGE)
        );
        TagResponse response = tagMapper.toTagResponse(tag);
        try {
            cacheManagerService.put(id.toString(), CachePrefix.TAG, response);
        } catch (Exception e) {
            throw new RedisNotSerializableException(e.getMessage());
        }
        return response;
    }
    @Transactional
    public Long create(TagRequest request) {
        Tag tag = tagMapper.toTag(request);
        Long id = tagRepository.save(tag).getId();
        cacheManagerService.delete(CachePrefix.TAG);
        return id;
    }
    @Transactional
    public Boolean update(Long id, TagRequest request) {
        Tag tagById = tagRepository.findById(id).orElseThrow(
                () -> new GenericNotFoundException(EXCEPTION_MESSAGE)
        );
        tagMapper.updateFromRequest(request, tagById);
        tagRepository.save(tagById);
        cacheManagerService.delete(CachePrefix.TAG);
        return true;
    }

    @Transactional(readOnly = true)
    public ApiResponse<List<TagResponse>> getAll(String search) {
        Object data = cacheManagerService.get(String.valueOf(search.hashCode()), CachePrefix.TAG);
        if (data != null) {
            return (ApiResponse<List<TagResponse>>) data;
        }
        List<TagResponse> response = tagRepository.findAllCustom(search);
        try {
            cacheManagerService.put(String.valueOf(search.hashCode()), CachePrefix.TAG, new ApiResponse<>(response));
        } catch (Exception e) {
            throw new RedisNotSerializableException(e.getMessage());
        }
        return new ApiResponse<>(response);
    }

    @Transactional
    public Boolean delete(Long id) {
        Tag tag = tagRepository.findById(id).orElseThrow(
                () -> new GenericNotFoundException(EXCEPTION_MESSAGE)
        );
        tag.makeAsDeleted();
        tagRepository.save(tag);
        cacheManagerService.delete(CachePrefix.TAG);
        return true;
    }
    @Transactional(readOnly = true)
    public TagResponse getByName(String name) {
        Tag tag = tagRepository.findByTagName(name);
        return getOne(tag.getId());
    }
}
