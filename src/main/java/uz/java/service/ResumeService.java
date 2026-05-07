package uz.java.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import uz.java.dto.resume.ResumeRequest;
import uz.java.dto.resume.ResumeResponse;
import uz.java.entity.employer.Profession;
import uz.java.entity.jobseeker.*;
import uz.java.exception.GenericNotFoundException;
import uz.java.mapper.CertificateMapper;
import uz.java.mapper.ResumeMapper;
import uz.java.mapper.SkillMapper;
import uz.java.mapper.WorkExperienceMapper;
import uz.java.repository.*;

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
    private final ProfessionRepository professionRepository;
    private final EducationRepository educationRepository;
    private final WorkExperienceMapper workExperienceMapper;
    private static final String EXCEPTION_MESSAGE = "resume.not.found";

    public ResumeResponse getOne(Long id) {

        Resume resume = resumeRepository.findById(id).orElseThrow(
                () -> new GenericNotFoundException(EXCEPTION_MESSAGE)
        );
        ResumeResponse response = resumeMapper.toResponse(resume);
        List<Certificate> certificateList = resume.getCertificateList();
        List<ResumeResponse.CertificateShortResponse> list = certificateList.stream()
                        .map(certificateMapper::toShortResponse).toList();

        response.setCertificateList(list);
        Set<Skill> skills = resume.getSkills();
        Set<ResumeResponse.SkillResponse> skillSet = skills.stream()
                .map(skillMapper::toResponse).collect(Collectors.toSet());
        response.setSkills(skillSet);

        return resumeMapper.toResponse(resume);
    }

    public Long create(ResumeRequest request) {
        Profession profession = professionRepository.findById(request.getProfessionId())
                .orElseThrow(() -> new GenericNotFoundException(EXCEPTION_MESSAGE));

        Set<Skill> skills = new HashSet<>();
        if (!Objects.isNull(request.getSkillIds())) {
            request.getSkillIds().stream().forEach(skillDto -> {
                Skill skill = skillRepository.findById(skillDto.getId()).orElseThrow(
                        () -> new GenericNotFoundException(EXCEPTION_MESSAGE + skillDto)
                );
                skills.add(skill);
            });
        }

        List<Education> educations = new ArrayList<>();
        if (request.getEducationIds() != null) {
            request.getEducationIds().forEach(eduId -> {
                Education education = educationRepository.findById(eduId)
                        .orElseThrow(() -> new GenericNotFoundException(EXCEPTION_MESSAGE + eduId));
                educations.add(education);
            });
        }

        List<WorkExperience> workExperiences = new ArrayList<>();
        if (request.getWorkExperienceIds() != null) {
            workExperiences = request.getWorkExperienceIds().stream()
                    .map(workExperienceMapper::toEntity)
                    .toList();
        }

        Resume resume = resumeMapper.toEntity(request);
        resume.setProfession(profession);
        resume.setSkills(skills);
        resume.setEducationList(educations);
        resume.setWorkExperienceList(workExperiences);

        Resume save = resumeRepository.save(resume);
        return save.getId();
    }

    public Long update(Long id, ResumeRequest request) {
        List<Certificate> certificateList = new ArrayList<>();
        Resume resume = resumeRepository.findById(id).orElseThrow(
                () -> new GenericNotFoundException(EXCEPTION_MESSAGE)
        );
        if (request.getProfessionId() != null) {
            Profession profession = professionRepository.findById(request.getProfessionId())
                    .orElseThrow(() -> new GenericNotFoundException(EXCEPTION_MESSAGE));
            resume.setProfession(profession);
        }

        if (request.getSkillIds() != null) {
            Set<Skill> skills = request.getSkillIds().stream()
                    .map(skillDto -> skillRepository.findById(skillDto.getId())
                            .orElseThrow(() -> new GenericNotFoundException(EXCEPTION_MESSAGE + skillDto.getId())))
                    .collect(Collectors.toSet());
            resume.setSkills(skills);
        }

        if (request.getEducationIds() != null) {
            List<Education> educations = request.getEducationIds().stream()
                    .map(eduId -> educationRepository.findById(eduId)
                            .orElseThrow(() -> new GenericNotFoundException(EXCEPTION_MESSAGE + eduId)))
                    .toList();
            resume.setEducationList(educations);
        }

        if (request.getWorkExperienceIds() != null) {
            List<WorkExperience> workExperiences = request.getWorkExperienceIds().stream()
                    .map(workExperienceMapper::toEntity)
                    .toList();
            resume.setWorkExperienceList(workExperiences);
        }

        resumeMapper.updateFromRequest(request, resume);
        resumeRepository.save(resume);
        return id;
    }
}
