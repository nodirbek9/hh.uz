package uz.java.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import uz.java.dto.company.CompanyRequest;
import uz.java.dto.company.CompanyResponse;
import uz.java.entity.employer.Company;
import uz.java.mapper.CompanyMapper;
import uz.java.repository.CompanyRepository;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CompanyService {

    private final CompanyRepository companyRepository;
    private final CompanyMapper companyMapper;
    private static final String EXCEPTION_MESSAGE = "Company is not found";

    public CompanyResponse getOne(Long id) {
        Company company = companyRepository.findById(id).orElseThrow(
                () -> new RuntimeException(EXCEPTION_MESSAGE)
        );
        // Optional class bu NullPointerException dan qochish uchun ishlatiladi, obyektni wrap qiladigan class
//        this class handles null object without throw NullPointerException
        return companyMapper.toResponse(company);
    }

    public Long create(CompanyRequest request) {
        Company company = companyMapper.toCompany(request);
        Company saved = companyRepository.save(company);
        return saved.getId();
    }

    public Boolean update(Long id, CompanyRequest request) {
        Optional<Company> opt = companyRepository.findById(id);
        if (!opt.isPresent()) {
            throw new RuntimeException("Company not found");
        }
        Company company = opt.get();
        companyMapper.updateFromRequest(request, company);
        companyRepository.save(company);
        return true;
    }

    public List<CompanyResponse> getAll() {
        return companyRepository.findAll().stream()
                .filter(c -> !c.isDeleted())
                .map(companyMapper::toResponse).toList();
    }

    public Boolean delete(Long id) {
        Company company = companyRepository.findById(id).orElseThrow(
                () -> new RuntimeException("Company not found")
        );
        // hard delete
//        companyRepository.delete(company);

        // soft delete
        company.makeAsDeleted();
        companyRepository.save(company);
        return true;
    }

    public CompanyResponse getByName(String name) {
        Company company = companyRepository.findByNomi(name);
        return getOne(company.getId());
    }


}
