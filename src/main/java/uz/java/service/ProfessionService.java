package uz.java.service;

import org.springframework.stereotype.Service;
import uz.java.dto.resume.ProfessionResponse;
import uz.java.entity.employer.Profession;
import uz.java.mapper.ProfessionMapper;
import uz.java.repository.ProfessionRepository;
import uz.java.specifications.SearchSpecification;

import java.util.List;

@Service
public class ProfessionService {
    private final ProfessionRepository professionRepository;
    private final ProfessionMapper mapper;

    public ProfessionService(ProfessionRepository professionRepository, ProfessionMapper mapper) {
        this.professionRepository = professionRepository;
        this.mapper = mapper;
    }

    public List<ProfessionResponse> getAll(Integer page, Integer limit, String sortBy) {
        return professionRepository.findAll(SearchSpecification.getPageable(
                page, limit, sortBy)).stream().map(mapper::toResponse).toList();
    }

    public List<ProfessionResponse> getProfessionByParentId(Long parentId) {
        List<Profession> list = professionRepository.findByParentId(parentId);
        return list.stream().map(mapper::toResponse).toList();
    }
}
