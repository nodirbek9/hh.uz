package uz.java.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import uz.java.dto.cache.ApiResponse;
import uz.java.dto.cache.CacheDto;
import uz.java.dto.resume.CertificateFilter;
import uz.java.dto.resume.CertificateRequest;
import uz.java.dto.resume.CertificateShortResponse;
import uz.java.entity.jobseeker.Certificate;
import uz.java.entity.jobseeker.Resume;
import uz.java.exception.GenericNotFoundException;
import uz.java.exception.RedisNotSerializableException;
import uz.java.mapper.CertificateMapper;
import uz.java.repository.CertificateRepository;
import uz.java.repository.ResumeRepository;
import uz.java.specifications.SearchSpecification;
import uz.java.util.CachePrefix;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CertificateService {

    private final CertificateRepository repository;
    private final ResumeRepository resumeRepository;
    private final CertificateMapper mapper;
    private final CacheManagerService cacheManagerService;

    @Transactional(readOnly = true)
    public CertificateShortResponse getOne(Long id) {
        Object data = cacheManagerService.get(id.toString(), CachePrefix.CERTIFICATE);
        if (data != null)
            return (CertificateShortResponse) data;

        Certificate certificate = repository.findById(id).orElseThrow(
                () -> new GenericNotFoundException("certificate.not.found")
        );
        CertificateShortResponse response = mapper.toShortResponse(certificate);

        try {
            cacheManagerService.put(id.toString(), CachePrefix.CERTIFICATE, response);
        } catch (Exception e) {
            throw new RedisNotSerializableException(e.getMessage());
        }

        return response;
    }

    @Transactional
    public Long create(CertificateRequest request) {

        Resume resume = resumeRepository.findById(request.getResumeId()).orElseThrow(
                () -> new GenericNotFoundException("resume.not.found")
        );
        Certificate certificate = mapper.toEntity(request);
        certificate.setResume(resume);
        Long id = repository.save(certificate).getId();
        cacheManagerService.delete(CachePrefix.CERTIFICATE);
        return id;
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
        cacheManagerService.delete(CachePrefix.CERTIFICATE);
        return true;
    }

    @Transactional(readOnly = true)
    public ApiResponse<List<CertificateShortResponse>> getAll(CertificateFilter filter) {
        Object data = cacheManagerService.get(String.valueOf(filter.hashCode()), CachePrefix.CERTIFICATE);
        if (data != null) {
            return (ApiResponse<List<CertificateShortResponse>>) data;
        }
        List<CertificateShortResponse> response = repository.findAllCustom(filter.getName() != null ? filter.getName() : "", SearchSpecification.getPageable(filter.getPage(),
                filter.getLimit(), filter.getSortBy()));
        try {
            cacheManagerService.put(String.valueOf(filter.hashCode()), CachePrefix.CERTIFICATE, new ApiResponse<>(response));
        } catch (Exception e) {
            throw new RedisNotSerializableException(e.getMessage());
        }
        return new ApiResponse<>(response);
    }
    //example
//    @Override
//    @Transactional(readOnly = true)
//    public ApiResponse<List<ProjectInfo>> getAll(ProjectFilter projectFilter) {
//        Object data = cacheManagerService.get(String.valueOf(projectFilter.hashCode()), CachePrefix.PROJECT);
//        if (data != null) {
//            return (ApiResponse<List<ProjectInfo>>) data;
//        }
//        List<Project> all = repository.findAllCustom();
//        List<ProjectInfo> response = all.stream().map(mapper::toResponse).toList();
//        cacheManagerService.put(String.valueOf(projectFilter.hashCode()), CachePrefix.PROJECT, new ApiResponse<>(response));
//        return new ApiResponse<>(response);
//    }

    @Transactional
    public Boolean delete(Long id) {
        Certificate certificate = repository.findById(id).orElseThrow(
                () -> new GenericNotFoundException("certificate.not.found")
        );

        certificate.makeAsDeleted();
        repository.save(certificate);
        cacheManagerService.delete(CachePrefix.CERTIFICATE);
        return true;
    }

    @Transactional(readOnly = true)
    public CertificateShortResponse getByName(String name) {
        Certificate certificate = repository.findByNameCustom(name);
        return getOne(certificate.getId());
    }
}
