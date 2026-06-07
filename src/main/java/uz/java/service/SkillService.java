package uz.java.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import uz.java.dto.resume.SkillRequest;
import uz.java.dto.resume.SkillResponse;
import uz.java.entity.jobseeker.Skill;
import uz.java.exception.GenericNotFoundException;
import uz.java.mapper.SkillMapper;
import uz.java.repository.SkillRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
public class SkillService {

    private final SkillRepository repository;
    private final SkillMapper mapper;
    private static final String EXCEPTION_MESSAGE = "skill.not.found";

    @Transactional(readOnly = true)
    public SkillResponse getOne(Long id) {
        Skill skill = repository.findById(id).orElseThrow(
                () -> new GenericNotFoundException(EXCEPTION_MESSAGE)
        );
        return mapper.toResponse(skill);
    }

    public Long create(SkillRequest request) {
        Skill skill = mapper.toEntity(request);
        return repository.save(skill).getId();
    }

    public Boolean update(Long id, SkillRequest request) {
        Skill oldskill = repository.findById(id).orElseThrow(
                () -> new GenericNotFoundException(EXCEPTION_MESSAGE)
        );
        mapper.updateFromRequest(request, oldskill);
        repository.save(oldskill);
        return true;
    }

    @Transactional(readOnly = true)
    public List<SkillResponse> getAll(String search) {
        return repository.findAllCustom(search);
    }

    public Boolean delete(Long id) {
        Skill skill = repository.findById(id).orElseThrow(
                () -> new GenericNotFoundException(EXCEPTION_MESSAGE)
        );

        skill.makeAsDeleted();
        repository.save(skill);
        return true;
    }

    @Transactional(readOnly = true)
    public SkillResponse getByName(String name) {
        Skill skill = repository.findByNameCustom(name);
        return getOne(skill.getId());
    }
}
