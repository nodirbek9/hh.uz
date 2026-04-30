package uz.java.service;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import uz.java.dto.resume.ResumeRequest;
import uz.java.dto.resume.ResumeResponse;
import uz.java.entity.jobseeker.Certificate;
import uz.java.entity.jobseeker.Resume;
import uz.java.entity.jobseeker.Skill;
import uz.java.mapper.CertificateMapper;
import uz.java.mapper.ResumeMapper;
import uz.java.mapper.SkillMapper;
import uz.java.repository.CertificateRepository;
import uz.java.repository.ResumeRepository;
import uz.java.repository.SkillRepository;

import java.util.*;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class ResumeService {

    private final ResumeRepository resumeRepository;
    private final ResumeMapper resumeMapper;
    private final CertificateMapper certificateMapper;
    private final SkillMapper skillMapper;
    private final CertificateRepository certificateRepository;
    private final SkillRepository skillRepository;

    public ResumeResponse getOne(Long id) {

        Resume resume = resumeRepository.findById(id).orElseThrow(
                () -> new RuntimeException("Resume not found")
        );
        ResumeResponse response = resumeMapper.toResponse(resume);
        List<Certificate> certificateList = resume.getCertificateList();
        List<ResumeResponse.CertificateShortResponse> list = certificateList.stream()
                        .map(certificateMapper::toStringResponse).toList();

        response.setCertificateList(list);
        Set<Skill> skills = resume.getSkills();
        Set<ResumeResponse.SkillResponse> skillSet = skills.stream()
                .map(skillMapper::toResponse).collect(Collectors.toSet());
        response.setSkills(skillSet);

        return resumeMapper.toResponse(resume);
    }

    public Long create(ResumeRequest request) {
        List<Certificate> certificateList = new ArrayList<>();
        if (!Objects.isNull(request.getCertificateIds())) {
            request.getCertificateIds().stream().forEach(id -> {
                Certificate certificate = certificateRepository.findById(id).orElseThrow(
                        () -> new RuntimeException("Certificate not found with this id: " + id)
                );
                certificateList.add(certificate);
            });
        }
        Set<Skill> skills = new HashSet<>();
        if (!Objects.isNull(request.getSkillIds())) {
            request.getSkillIds().stream().forEach(id -> {
                Skill skill = skillRepository.findById(id).orElseThrow(
                        () -> new RuntimeException("Skill not found with this id: " + id)
                );
                skills.add(skill);
            });
        }
        Resume entity = resumeMapper.toEntity(request);
        entity.setSkills(skills);
        entity.setCertificateList(certificateList);
        Resume save = resumeRepository.save(entity);
        return save.getId();
    }

    public Object update(Long id, ResumeRequest request) {
        List<Certificate> certificateList = new ArrayList<>();

        if (!Objects.isNull(request.getCertificateIds())) {
            request.getCertificateIds().stream().forEach(element -> {
                Certificate certificate = certificateRepository.findById(element).orElseThrow(
                        () -> new RuntimeException("Certificate not found with this id: " + element)
                );
                certificateList.add(certificate);
            });
        }

        Set<Skill> skills = new HashSet<>();

        if (!Objects.isNull(request.getSkillIds())) {
            request.getSkillIds().stream().forEach(element -> {
                Skill skill = skillRepository.findById(element).orElseThrow(
                        () -> new RuntimeException("Skill not found with this id: " + id)
                );
                skills.add(skill);
            });
        }

        Resume resume = resumeRepository.findById(id).orElseThrow(
                () -> new RuntimeException("Resume not found")
        );

        resume.setSkills(skills);
        resume.setCertificateList(certificateList);
        resumeMapper.updateFromRequest(resume, request);
        resumeRepository.save(resume);
        return true;
    }
}
