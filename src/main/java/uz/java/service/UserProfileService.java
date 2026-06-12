package uz.java.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import uz.java.dto.resume.EducationResponse;
import uz.java.dto.user.LanguageResponse;
import uz.java.dto.user.UserProfileRequest;
import uz.java.dto.user.UserProfileResponse;
import uz.java.dto.user.WorkExperienceResponse;
import uz.java.entity.jobseeker.Education;
import uz.java.entity.jobseeker.Language;
import uz.java.entity.jobseeker.WorkExperience;
import uz.java.entity.user.UserProfile;
import uz.java.exception.GenericNotFoundException;
import uz.java.exception.RedisNotSerializableException;
import uz.java.mapper.EducationMapper;
import uz.java.mapper.LanguageMapper;
import uz.java.mapper.UserProfileMapper;
import uz.java.mapper.WorkExperienceMapper;
import uz.java.repository.EducationRepository;
import uz.java.repository.LanguageRepository;
import uz.java.repository.UserProfileRepository;
import uz.java.repository.WorkExperienceRepository;
import uz.java.util.CachePrefix;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserProfileService {

    private final UserProfileRepository repository;
    private final EducationRepository educationRepository;
    private final WorkExperienceRepository workExperienceRepository;
    private final LanguageRepository languageRepository;
    private final UserProfileMapper userProfileMapper;
    private final EducationMapper educationMapper;
    private final WorkExperienceMapper workExperienceMapper;
    private final LanguageMapper languageMapper;
    private final CacheManagerService cacheManagerService;
    private static final String EXCEPTION_MESSAGE = "userProfile.not.found";

    @Transactional(readOnly = true)
    public UserProfileResponse getUserProfile(Long id) {
        Object data = cacheManagerService.get(id.toString(), CachePrefix.USER_PROFILE);
        if (data != null)
            return (UserProfileResponse) data;

        UserProfile profile = repository.findById(id).orElseThrow(
                () -> new GenericNotFoundException(EXCEPTION_MESSAGE)
        );
        List<Education> list = educationRepository.findByUserProfileId(id);
        List<EducationResponse> educationResponseList = list.stream().map(educationMapper::toResponse).toList();
        UserProfileResponse response = userProfileMapper.toResponse(profile);
        response.setEducations(educationResponseList);

        List<WorkExperience> workExperienceList = workExperienceRepository.findByUserProfileId(id);
        List<WorkExperienceResponse> workExperienceResponseList = workExperienceList.stream().map(workExperienceMapper::toResponse).toList();
        response.setWorkExperiences(workExperienceResponseList);

        List<Language> languageList = languageRepository.findByUserProfileId(id);
        Set<LanguageResponse> languageResponseList = languageList.stream().map(languageMapper::toResponse).collect(Collectors.toSet());
        response.setLanguageSet(languageResponseList);

        try {
            cacheManagerService.put(id.toString(), CachePrefix.USER_PROFILE, response);
        } catch (Exception e) {
            throw new RedisNotSerializableException(e.getMessage());
        }
        return response;
    }

    @Transactional
    public Long update(Long id, UserProfileRequest request) {
        UserProfile profile = repository.findById(id).orElseThrow(
                () -> new GenericNotFoundException(EXCEPTION_MESSAGE)
        );

        if (request.getLanguages() != null && !request.getLanguages().isEmpty()) {
            Set<Language> collect = request.getLanguages().stream().map(languageDto -> {
                if (languageDto.getId() != null) {
                    return languageRepository.findById(languageDto.getId())
                            .orElseThrow(() -> new GenericNotFoundException(EXCEPTION_MESSAGE +
                                    languageDto.getId()));
                }
                return languageMapper.toEntity(languageDto);
            }).collect(Collectors.toSet());
            profile.setLanguages(collect);
        }
        userProfileMapper.updateFromRequest(request, profile);
        repository.save(profile);
        cacheManagerService.delete(CachePrefix.USER_PROFILE);
        return profile.getId();
    }

    @Transactional
    public Boolean delete(Long id) {
        UserProfile userProfile = repository.findById(id).orElseThrow(
                () -> new GenericNotFoundException(EXCEPTION_MESSAGE)
        );
        userProfile.makeAsDeleted();
        repository.save(userProfile);
        cacheManagerService.delete(CachePrefix.USER_PROFILE);
        return true;
    }
}
