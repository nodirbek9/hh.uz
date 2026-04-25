package uz.java.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import uz.java.dto.tag.TagRequest;
import uz.java.dto.tag.TagResponse;
import uz.java.entity.employer.Tag;
import uz.java.mapper.TagMapper;
import uz.java.repository.TagRepository;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class TagService {

    private final TagRepository tagRepository;
    private final TagMapper tagMapper;
    private static final String EXCEPTION_MESSAGE = "Tag is not found";


    public TagResponse getOne(Long id) {
        Optional<Tag> tagById = tagRepository.findById(id);

        if (!tagById.isPresent()) {
            throw new RuntimeException(EXCEPTION_MESSAGE);
        }
        Tag tag = tagById.get();
        return tagMapper.toTagResponse(tag);

    }

    public Long create(TagRequest request) {
        Tag tag = tagMapper.toTag(request);
        return tagRepository.save(tag).getId();
    }

    public Boolean update(Long id, TagRequest request) {
        Tag tagById = tagRepository.findById(id).orElseThrow(
                () -> new RuntimeException(EXCEPTION_MESSAGE)
        );
        tagMapper.updateFromRequest(request, tagById);
        tagRepository.save(tagById);
        return true;
    }

    public List<TagResponse> getAll() {
        return tagRepository.findAll().stream().filter(
                        t -> !t.isDeleted())
                .map(tagMapper::toTagResponse).toList();
    }

    public Boolean delete(Long id) {
        Tag tag = tagRepository.findById(id).orElseThrow(
                () -> new RuntimeException(EXCEPTION_MESSAGE)
        );
        tag.makeAsDeleted();
        tagRepository.save(tag);
        return true;
    }

    public TagResponse getByName(String name) {
        Tag tag = tagRepository.findByTagName(name);
        return getOne(tag.getId());
    }
}
