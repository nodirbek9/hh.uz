package uz.java.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import uz.java.dto.application.ApplicationRequest;
import uz.java.dto.application.ApplicationResponse;
import uz.java.dto.application.ApplicationStatusRequest;
import uz.java.entity.application.ApplicationStatusHistory;
import uz.java.entity.application.VacancyApplication;
import uz.java.entity.employer.Vacancy;
import uz.java.entity.enums.VacancyApplicationStatus;
import uz.java.entity.jobseeker.Resume;
import uz.java.entity.user.User;
import uz.java.exception.GenericNotFoundException;
import uz.java.exception.InvalidDataException;
import uz.java.repository.ResumeRepository;
import uz.java.repository.UserRepository;
import uz.java.repository.VacancyApplicationRepository;
import uz.java.repository.VacancyRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
public class VacancyApplicationService {

    private final VacancyApplicationRepository applicationRepository;
    private final VacancyRepository vacancyRepository;
    private final ResumeRepository resumeRepository;
    private final UserRepository userRepository;
    private final NotificationService notificationService;

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
        VacancyApplication application = applicationRepository.findById(id)
                .orElseThrow(() -> new GenericNotFoundException("application.not.found"));
        return toResponse(application);
    }

    @Transactional(readOnly = true)
    public List<ApplicationResponse> getMyApplication(Long userId) {
        return applicationRepository.findByUserId(userId)
                .stream().map(this::toResponse).toList();
    }

    @Transactional(readOnly = true)
    public List<ApplicationResponse> getByVacancy(Long vacancyId) {
        return applicationRepository.findByVacancyId(vacancyId)
                .stream().map(this::toResponse).toList();
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
