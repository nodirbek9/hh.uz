package uz.java.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import uz.java.dto.resume.CertificateFilter;
import uz.java.dto.resume.CertificateRequest;
import uz.java.dto.resume.CertificateShortResponse;
import uz.java.entity.jobseeker.Certificate;
import uz.java.entity.jobseeker.Resume;
import uz.java.exception.GenericNotFoundException;
import uz.java.mapper.CertificateMapper;
import uz.java.repository.CertificateRepository;
import uz.java.repository.ResumeRepository;
import uz.java.specifications.SearchSpecification;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CertificateService {

    private final CertificateRepository repository;
    private final ResumeRepository resumeRepository;
    private final CertificateMapper mapper;

    @Transactional(readOnly = true)
    public CertificateShortResponse getOne(Long id) {
        Certificate certificate = repository.findById(id).orElseThrow(
                () -> new GenericNotFoundException("certificate.not.found")
        );
        return mapper.toShortResponse(certificate);
    }

    @Transactional
    public Long create(CertificateRequest request) {

        Resume resume = resumeRepository.findById(request.getResumeId()).orElseThrow(
                () -> new GenericNotFoundException("resume.not.found")
        );
        Certificate certificate = mapper.toEntity(request);
        certificate.setResume(resume);
        return repository.save(certificate).getId();
    }

    @Transactional
    public Boolean update(Long id, CertificateRequest request) {
        Certificate oldCertificate = repository.findById(id).orElseThrow(
                () -> new GenericNotFoundException("certificate.not.found")
        );
        Resume resume = resumeRepository.findById(request.getResumeId()).orElseThrow(
                () -> new GenericNotFoundException("resume.not.found")
        );
        mapper.updateFromRequest(request, oldCertificate);
        oldCertificate.setResume(resume);
        repository.save(oldCertificate);
        return true;
    }

    @Transactional(readOnly = true)
    public List<CertificateShortResponse> getAll(CertificateFilter filter) {
//        return repository.findAllCustom(filter.getName() != null ? filter.getName() : "", SearchSpecification.getPageable(filter.getPage(),
//                filter.getLimit(), filter.getSortBy())).stream().map(mapper::toShortResponse).toList();
        return repository.findAllCustom(filter.getName() != null ? filter.getName() : "", SearchSpecification.getPageable(filter.getPage(),
                filter.getLimit(), filter.getSortBy()));
    }

    @Transactional
    public Boolean delete(Long id) {
        Certificate certificate = repository.findById(id).orElseThrow(
                () -> new GenericNotFoundException("certificate.not.found")
        );

        certificate.makeAsDeleted();
        repository.save(certificate);
        return true;
    }

    @Transactional(readOnly = true)
    public CertificateShortResponse getByName(String name) {
        Certificate certificate = repository.findByNameCustom(name);
        return getOne(certificate.getId());
    }
}
