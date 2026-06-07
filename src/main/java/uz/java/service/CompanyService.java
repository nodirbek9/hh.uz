package uz.java.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import uz.java.dto.company.CompanyFilter;
import uz.java.dto.company.CompanyRequest;
import uz.java.dto.company.CompanyResponse;
import uz.java.entity.employer.Company;
import uz.java.exception.GenericNotFoundException;
import uz.java.exception.InvalidDataException;
import uz.java.mapper.CompanyMapper;
import uz.java.repository.CompanyRepository;
import uz.java.specifications.CompanySpecification;
import uz.java.specifications.SearchSpecification;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CompanyService {

    private final CompanyRepository companyRepository;
    private final CompanyMapper companyMapper;
    private static final String EXCEPTION_MESSAGE = "company.not.found";

    @Transactional(readOnly = true)
    public CompanyResponse getOne(Long id) {
        Company company = companyRepository.findById(id).orElseThrow(
                () -> new GenericNotFoundException(EXCEPTION_MESSAGE)
        );
        // Optional class bu NullPointerException dan qochish uchun ishlatiladi, obyektni wrap qiladigan class
//        this class handles null object without throw NullPointerException
        return companyMapper.toResponse(company);
    }

    @Transactional
    public Long create(CompanyRequest request) {
        if (request.getEmail() == null || request.getEmail().isBlank()) {
            throw new InvalidDataException("invalid.email");
        }
        Company company = companyMapper.toCompany(request);
        Company saved = companyRepository.save(company);
        return saved.getId();
    }

    @Transactional
    public Boolean update(Long id, CompanyRequest request) {
        Company company = companyRepository.findById(id).orElseThrow(
                () -> new GenericNotFoundException(EXCEPTION_MESSAGE)
        );
        companyMapper.updateFromRequest(request, company);
        companyRepository.save(company);
        return true;
    }

    @Transactional(readOnly = true)
    public List<CompanyResponse> getAll(CompanyFilter filter) {
        CompanySpecification spec = new CompanySpecification(filter);
        return companyRepository.findAll(spec, SearchSpecification.getPageable(filter.getPage(),
                        filter.getLimit(), filter.getSortBy())).stream()
                .filter(c -> !c.isDeleted())
                .map(companyMapper::toResponse).toList();
    }

    @Transactional
    public Boolean delete(Long id) {
        Company company = companyRepository.findById(id).orElseThrow(
                () -> new GenericNotFoundException(EXCEPTION_MESSAGE)
        );
        // hard delete
//        companyRepository.delete(company);

        // soft delete
        company.makeAsDeleted();
        companyRepository.save(company);
        return true;
    }

    @Transactional(readOnly = true)
    public CompanyResponse getByName(String name) {
        Company company = companyRepository.findByNomi(name);
        return getOne(company.getId());
    }
}
