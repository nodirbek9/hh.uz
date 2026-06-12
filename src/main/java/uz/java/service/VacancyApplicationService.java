package uz.java.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import uz.java.dto.application.ApplicationRequest;
import uz.java.dto.application.ApplicationResponse;
import uz.java.dto.application.ApplicationStatusRequest;
import uz.java.dto.cache.ApiResponse;
import uz.java.dto.cache.CacheDto;
import uz.java.entity.application.ApplicationStatusHistory;
import uz.java.entity.application.VacancyApplication;
import uz.java.entity.employer.Vacancy;
import uz.java.entity.enums.VacancyApplicationStatus;
import uz.java.entity.jobseeker.Resume;
import uz.java.entity.user.User;
import uz.java.exception.GenericNotFoundException;
import uz.java.exception.InvalidDataException;
import uz.java.exception.RedisNotSerializableException;
import uz.java.repository.ResumeRepository;
import uz.java.repository.UserRepository;
import uz.java.repository.VacancyApplicationRepository;
import uz.java.repository.VacancyRepository;
import uz.java.util.CachePrefix;

import java.util.List;

@Service
@RequiredArgsConstructor
public class VacancyApplicationService {

    private final VacancyApplicationRepository applicationRepository;
    private final VacancyRepository vacancyRepository;
    private final ResumeRepository resumeRepository;
    private final UserRepository userRepository;
    private final NotificationService notificationService;
    private final CacheManagerService cacheManagerService;

    @Transactional
    public Long apply(ApplicationRequest request, Long userId) {
        if (applicationRepository.existsByVacancyIdAndUserId(request.getVacancyId(), userId))
            throw new InvalidDataException("application.already.exists");

        Vacancy vacancy = vacancyRepository.findById(request.getVacancyId())
                .orElseThrow(() -> new GenericNotFoundException("vacancy.not.found"));
        Resume resume = resumeRepository.findById(request.getResumeId())
                .orElseThrow(() -> new GenericNotFoundException("resume.not.found"));
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new GenericNotFoundException("user.not.found"));

        VacancyApplication application = new VacancyApplication();
        application.setVacancy(vacancy);
        application.setResume(resume);
        application.setUser(user);
        application.setLetter(request.getLetter());
        application.setStatus(VacancyApplicationStatus.SEND);

        VacancyApplication saved = applicationRepository.save(application);
        cacheManagerService.delete(CachePrefix.APPLICATION);

        Long employerUserId = vacancy.getCompany().getUser().getId();
        notificationService.send(
                employerUserId,
                "NEW_APPLICATION",
                "Yangi ariza keldi",
                "\"" + vacancy.getName() + "\" vakansiyasiga yangi ariza topshirildi"
        );
        return saved.getId();
    }

    @Transactional(readOnly = true)
    public ApplicationResponse getOne(Long id) {
        Object data = cacheManagerService.get(id.toString(), CachePrefix.APPLICATION);
        if (data != null)
            return (ApplicationResponse) data;

        VacancyApplication application = applicationRepository.findById(id)
                .orElseThrow(() -> new GenericNotFoundException("application.not.found"));
        ApplicationResponse response = toResponse(application);
        try {
            cacheManagerService.put(id.toString(), CachePrefix.APPLICATION, response);
        } catch (Exception e) {
            throw new RedisNotSerializableException(e.getMessage());
        }
        return response;
    }

    @Transactional(readOnly = true)
    public ApiResponse<List<ApplicationResponse>> getMyApplication(Long userId) {
        Object data = cacheManagerService.get("user_" + userId, CachePrefix.APPLICATION);
        if (data != null) {
            return (ApiResponse<List<ApplicationResponse>>) data;
        }
        List<ApplicationResponse> response = applicationRepository.findByUserId(userId)
                .stream().map(this::toResponse).toList();
        try {
            cacheManagerService.put("user_" + userId, CachePrefix.APPLICATION, new ApiResponse<>(response));
        } catch (Exception e) {
            throw new RedisNotSerializableException(e.getMessage());
        }
        return new ApiResponse<>(response);
    }

    @Transactional(readOnly = true)
    public ApiResponse<List<ApplicationResponse>> getByVacancy(Long vacancyId) {
        Object data = cacheManagerService.get("vacancy_" + vacancyId, CachePrefix.APPLICATION);
        if (data != null) {
            return (ApiResponse<List<ApplicationResponse>>) data;
        }
        List<ApplicationResponse> response = applicationRepository.findByVacancyId(vacancyId)
                .stream().map(this::toResponse).toList();
        try {
            cacheManagerService.put("vacancy_" + vacancyId, CachePrefix.APPLICATION, new ApiResponse<>(response));
        } catch (Exception e) {
            throw new RedisNotSerializableException(e.getMessage());
        }
        return new ApiResponse<>(response);
    }

    @Transactional
    public Boolean updateStatus(Long id, ApplicationStatusRequest request) {
        VacancyApplication app = applicationRepository.findById(id).orElseThrow(
                        () -> new GenericNotFoundException("application.not.found"));

        ApplicationStatusHistory history = new ApplicationStatusHistory();
        history.setApplication(app);
        history.setOldStatus(app.getStatus().name());
        history.setNewStatus(request.getStatus().name());
        history.setComment(request.getComment());

        app.setStatus(request.getStatus());
        applicationRepository.save(app);
        cacheManagerService.delete(CachePrefix.APPLICATION);
        return true;
    }

    private ApplicationResponse toResponse(VacancyApplication app) {
        ApplicationResponse response = new ApplicationResponse();
        response.setId(app.getId());
        response.setVacancyId(app.getVacancy().getId());
        response.setVacancyName(app.getVacancy().getName());
        response.setUserId(app.getUser().getId());
        response.setResumeId(app.getResume().getId());
        response.setLetter(app.getLetter());
        response.setStatus(app.getStatus().name());
        response.setCreatedAt(app.getCreatedAt());
        return response;
    }
}
