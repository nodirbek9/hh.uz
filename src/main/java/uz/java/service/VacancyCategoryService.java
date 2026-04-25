package uz.java.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import uz.java.dto.tag.TagResponse;
import uz.java.dto.vacancy.VacancyCategoryRequest;
import uz.java.dto.vacancy.VacancyCategoryResponse;
import uz.java.entity.employer.VacancyCategory;
import uz.java.mapper.VacancyCategoryMapper;
import uz.java.repository.VacancyCategoryRepository;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class VacancyCategoryService {

    private final VacancyCategoryRepository repository;
    private final VacancyCategoryMapper mapper;
    private static final String EXCEPTION_MESSAGE = "VacancyCategory is not found";

    public VacancyCategoryResponse getOne(Long id) {
        Optional<VacancyCategory> optional = repository.findById(id);

        if (!optional.isPresent()) {
            throw new RuntimeException(EXCEPTION_MESSAGE);
        }
        VacancyCategory vacancyCategory = optional.get();
        return mapper.toVacancyCategoryResponse(vacancyCategory);
    }


    public Long create(VacancyCategoryRequest request) {
        VacancyCategory category = mapper.toVacancyCategory(request);
        return repository.save(category).getId();
    }


    public Boolean update(Long id, VacancyCategoryRequest request) {
        VacancyCategory findId = repository.findById(id).orElseThrow(
                () -> new RuntimeException(EXCEPTION_MESSAGE)
        );
        mapper.updateVacancyCategory(request, findId);
        repository.save(findId);
        return true;
    }

    public List<VacancyCategoryResponse> getAll() {
        return repository.findAll().stream()
                .filter(v -> !v.isDeleted())
                .map(mapper::toVacancyCategoryResponse).toList();
    }

    public Boolean delete(Long id) {
        VacancyCategory category = repository.findById(id).orElseThrow(
                () -> new RuntimeException(EXCEPTION_MESSAGE)
        );
        category.makeAsDeleted();
        repository.save(category);
        return true;
    }

    public VacancyCategoryResponse getByName(String name) {
        VacancyCategory category = repository.findVacancyCategoriesByName(name);
        return getOne(category.getId());
    }
}
