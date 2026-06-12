package uz.java.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import uz.java.dto.cache.ApiResponse;
import uz.java.dto.cache.CacheDto;
import uz.java.dto.resume.*;
import uz.java.entity.employer.Profession;
import uz.java.entity.jobseeker.*;
import uz.java.exception.GenericNotFoundException;
import uz.java.exception.RedisNotSerializableException;
import uz.java.mapper.*;
import uz.java.repository.EducationRepository;
import uz.java.repository.ProfessionRepository;
import uz.java.repository.ResumeRepository;
import uz.java.repository.SkillRepository;
import uz.java.util.CachePrefix;

import java.util.*;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class ResumeService {

    private final ResumeRepository resumeRepository;
    private final ResumeMapper resumeMapper;
    private final CertificateMapper certificateMapper;
    private final SkillMapper skillMapper;
    private final CourseMapper courseMapper;
    private final SkillRepository skillRepository;
    private final ProfessionRepository professionRepository;
    private final EducationRepository educationRepository;
    private final WorkExperienceMapper workExperienceMapper;
    private final CacheManagerService cacheManagerService;
    private static final String EXCEPTION_MESSAGE = "resume.not.found";

    @Transactional(readOnly = true)
    public ResumeResponse getOne(Long id) {
        Object data = cacheManagerService.get(id.toString(), CachePrefix.RESUME);
        if (data != null)
            return (ResumeResponse) data;

        Resume resume = resumeRepository.findById(id).orElseThrow(
                () -> new GenericNotFoundException(EXCEPTION_MESSAGE)
        );
        ResumeResponse response = resumeMapper.toResponse(resume);
        List<Certificate> certificateList = resume.getCertificateList();
        List<CertificateShortResponse> list = certificateList.stream()
                        .map(certificateMapper::toShortResponse).toList();

        response.setCertificateList(list);
        Set<Skill> skills = resume.getSkills();
        Set<SkillResponse> skillSet = skills.stream()
                .map(skillMapper::toResponse).collect(Collectors.toSet());
        response.setSkills(skillSet);

        List<CourseResponse> courses = resume.getCourseList()
                .stream().map(courseMapper::toResponse).toList();
        response.setCourseList(courses);

        List<PortfolioResponse> portfolios = resume.getPortfolioList()
                .stream().map(p -> {
                    PortfolioResponse pr = new PortfolioResponse();
                    pr.setId(p.getId());
                    pr.setImage(p.getImage());
                    return pr;
                }).toList();
        response.setPortfolioList(portfolios);

        try {
            cacheManagerService.put(id.toString(), CachePrefix.RESUME, response);
        } catch (Exception e) {
            throw new RedisNotSerializableException(e.getMessage());
        }
        return response;
    }

    @Transactional
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
        cacheManagerService.delete(CachePrefix.RESUME);
        return save.getId();
    }

    @Transactional
    public Long update(Long id, ResumeRequest request) {
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
        cacheManagerService.delete(CachePrefix.RESUME);
        return id;
    }

    @Transactional
    public Boolean delete(Long id) {
        Resume resume = resumeRepository.findById(id).orElseThrow(
                () -> new GenericNotFoundException(EXCEPTION_MESSAGE)
        );
        resume.makeAsDeleted();
        resumeRepository.save(resume);
        cacheManagerService.delete(CachePrefix.RESUME);
        return true;
    }
}
