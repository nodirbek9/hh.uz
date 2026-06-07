package uz.java.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import uz.java.dto.resume.ResumeResponse;
import uz.java.dto.vacancy.VacancyResponse;
import uz.java.entity.employer.Vacancy;
import uz.java.entity.jobseeker.Resume;
import uz.java.entity.saved.SavedResume;
import uz.java.entity.saved.SavedVacancy;
import uz.java.entity.user.User;
import uz.java.entity.user.UserProfile;
import uz.java.exception.GenericNotFoundException;
import uz.java.exception.InvalidDataException;
import uz.java.mapper.ResumeMapper;
import uz.java.mapper.VacancyMapper;
import uz.java.repository.*;

import java.util.List;

@Service
@RequiredArgsConstructor
public class SavedItemService {

    private final SavedVacancyRepository savedVacancyRepository;
    private final SavedResumeRepository savedResumeRepository;
    private final VacancyRepository vacancyRepository;
    private final ResumeRepository resumeRepository;
    private final UserRepository userRepository;
    private final UserProfileRepository userProfileRepository;
    private final VacancyMapper vacancyMapper;
    private final ResumeMapper resumeMapper;


    // ---- SAVED VACANCIES ----

    @Transactional
    public Long saveVacancy(Long vacancyId, Long userId) {
        if (savedVacancyRepository.existsByVacancyIdAndUserId(vacancyId, userId))
            throw new InvalidDataException("vacancy.already.saved");

        Vacancy vacancy = vacancyRepository.findById(vacancyId)
                .orElseThrow(() -> new GenericNotFoundException("vacancy.not.found"));
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new GenericNotFoundException("user.not.found"));

        SavedVacancy saved = new SavedVacancy();
        saved.setVacancy(vacancy);
        saved.setUser(user);
        return savedVacancyRepository.save(saved).getId();
    }

    @Transactional
    public Boolean removeSavedVacancy(Long vacancyId, Long userId) {
        SavedVacancy saved = savedVacancyRepository
                .findByVacancyIdAndUserId(vacancyId, userId)
                .orElseThrow(() -> new GenericNotFoundException("saved.vacancy.not.found"));
        savedVacancyRepository.delete(saved);
        return true;
    }

    @Transactional(readOnly = true)
    public List<VacancyResponse> getMySavedVacancies(Long userId) {
        return savedVacancyRepository.findByUserId(userId)
                .stream()
                .map(s -> vacancyMapper.toVacancyResponse(s.getVacancy()))
                .toList();
    }

    // ---- SAVED RESUMES ----

    @Transactional
    public Long saveResume(Long resumeId, Long userId) {
        if (savedResumeRepository.existsByResumeIdAndUserId(resumeId, userId))
            throw new InvalidDataException("resume.already.saved");

        Resume resume = resumeRepository.findById(resumeId)
                .orElseThrow(() -> new GenericNotFoundException("resume.not.found"));
        // SavedResume.user — UserProfile, shuning uchun userProfileRepository kerak
        // Soddalik uchun userId bo'yicha UserProfile topamiz
        UserProfile userProfile = userProfileRepository.findByUserId(userId)
                .orElseThrow(() -> new GenericNotFoundException("userProfile.not.found"));

        SavedResume saved = new SavedResume();
        saved.setResume(resume);
        saved.setUser(userProfile);
        return savedResumeRepository.save(saved).getId();
    }

    @Transactional(readOnly = true)
    public List<ResumeResponse> getMySavedResumes(Long userId) {
        UserProfile userProfile = userProfileRepository.findByUserId(userId)
                .orElseThrow(() -> new GenericNotFoundException("userProfile.not.found"));
        return savedResumeRepository.findByUserId(userProfile.getId())
                .stream()
                .map(s -> resumeMapper.toResponse(s.getResume()))
                .toList();
    }
}
