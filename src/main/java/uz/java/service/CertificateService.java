package uz.java.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import uz.java.dto.resume.CertificateRequest;
import uz.java.dto.resume.ResumeResponse;
import uz.java.entity.jobseeker.Certificate;
import uz.java.entity.jobseeker.Resume;
import uz.java.exception.GenericNotFoundException;
import uz.java.mapper.CertificateMapper;
import uz.java.repository.CertificateRepository;
import uz.java.repository.ResumeRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CertificateService {

    private final CertificateRepository repository;
    private final ResumeRepository resumeRepository;
    private final CertificateMapper mapper;

    public ResumeResponse.CertificateShortResponse getOne(Long id) {
        Certificate certificate = repository.findById(id).orElseThrow(
                () -> new GenericNotFoundException("certificate.not.found")
        );
        return mapper.toShortResponse(certificate);
    }

    public Long create(CertificateRequest request) {

        Resume resume = resumeRepository.findById(request.getResumeId()).orElseThrow(
                () -> new GenericNotFoundException("resume.not.found")
        );
        Certificate certificate = mapper.toEntity(request);
        certificate.setResume(resume);
        return repository.save(certificate).getId();
    }

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

    public List<ResumeResponse.CertificateShortResponse> getAll() {
        return repository.findAll().stream().filter(t -> !t.isDeleted()).map(mapper::toShortResponse).toList();
    }

    public Boolean delete(Long id) {
        Certificate certificate = repository.findById(id).orElseThrow(
                () -> new GenericNotFoundException("certificate.not.found")
        );

        certificate.makeAsDeleted();
        repository.save(certificate);
        return true;
    }

    public ResumeResponse.CertificateShortResponse getByName(String name) {
        Certificate certificate = repository.findByNameCustom(name);
        return getOne(certificate.getId());
    }
}
