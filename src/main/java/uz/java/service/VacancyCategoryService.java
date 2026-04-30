package uz.java.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import uz.java.dto.vacancy.VacancyCategoryRequest;
import uz.java.dto.vacancy.VacancyCategoryResponse;
import uz.java.entity.employer.Specializations;
import uz.java.mapper.VacancyCategoryMapper;
import uz.java.repository.VacancyCategoryRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
public class VacancyCategoryService {

    private final VacancyCategoryRepository repository;
    private final VacancyCategoryMapper mapper;

    private static final String EXCEPTION_MESSAGE = "Specializations is not found";

    public VacancyCategoryResponse getOne(Long id) {
        Specializations category = repository.findById(id).orElseThrow(
                () -> new RuntimeException(EXCEPTION_MESSAGE)
        );
        return mapper.toVacancyCategoryResponse(category);
    }

    public Long create(VacancyCategoryRequest request) {
        return repository.save(mapper.toVacancyCategory(request)).getId();
    }

    public Boolean update(Long id, VacancyCategoryRequest request) {
        Specializations oldCategory = repository.findById(id).orElseThrow(
                () -> new RuntimeException(EXCEPTION_MESSAGE)
        );
        mapper.updateVacancyCategory(request, oldCategory);
        repository.save(oldCategory);
        return true;
    }

    public List<VacancyCategoryResponse> getAll() {
        return repository.findAll().stream().
                filter(c -> !c.isDeleted()).map(mapper::toVacancyCategoryResponse).toList();
    }

    public Boolean delete(Long id) {
        Specializations category = repository.findById(id).orElseThrow(
                () -> new RuntimeException(EXCEPTION_MESSAGE)
        );

        category.makeAsDeleted();
        repository.save(category);
        return true;
    }

    public VacancyCategoryResponse getByName(String name) {
        Specializations category = repository.findByNameCustom(name);
        return getOne(category.getId());
    }
}
