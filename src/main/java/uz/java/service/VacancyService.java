package uz.java.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import uz.java.dto.vacancy.VacancyFilter;
import uz.java.dto.vacancy.VacancyRequest;
import uz.java.dto.vacancy.VacancyResponse;
import uz.java.entity.employer.Vacancy;
import uz.java.mapper.VacancyMapper;
import uz.java.repository.VacancyRepository;
import uz.java.specifications.SearchSpecification;
import uz.java.specifications.VacancySpecification;

import java.util.List;

@Service
@RequiredArgsConstructor
public class VacancyService {

    private final VacancyRepository repository;
    private final VacancyMapper mapper;
    private static final String EXCEPTION_MESSAGE = "Vacancy is not found";

    public VacancyResponse getOne(Long id) {
        Vacancy vacancy = repository.findById(id).orElseThrow(
                () -> new RuntimeException(EXCEPTION_MESSAGE)
        );
        return mapper.toVacancyResponse(vacancy);
    }

    public Long create(VacancyRequest request) {
        Vacancy vacancy = mapper.toVacancy(request);
        return repository.save(vacancy).getId();
    }

    public Boolean update(Long id, VacancyRequest request) {
        Vacancy vacancy = repository.findById(id).orElseThrow(
                () -> new RuntimeException(EXCEPTION_MESSAGE)
        );
        mapper.updateFromRequest(request, vacancy);
        repository.save(vacancy);
        return true;
    }

    public Boolean delete(Long id) {
        Vacancy vacancy = repository.findById(id).orElseThrow(
                () -> new RuntimeException(EXCEPTION_MESSAGE)
        );
        vacancy.makeAsDeleted();
        repository.save(vacancy);
        return true;
    }

    public VacancyResponse getByName(String name) {
        Vacancy vacancy = repository.findByName(name);
        return getOne(vacancy.getId());
    }

    public List<VacancyResponse> getAll(VacancyFilter filter) {
        VacancySpecification spec = new VacancySpecification(filter);
        return repository.findAll(spec, SearchSpecification.getPageable(filter.getPage(),
                filter.getLimit(), filter.getSortBy())).stream().filter(vacancy -> !vacancy.isDeleted())
                .map(mapper::toVacancyResponse).toList();
    }
}
