package uz.java.dto.user;

import lombok.Data;
import uz.java.dto.resume.EducationResponse;

import java.util.List;
import java.util.Set;

@Data
public class UserProfileResponse {

    private UserResponse user;

    private List<EducationResponse> educations;

    private List<WorkExperienceResponse> workExperiences;

    private Set<LanguageResponse> languageSet;
}
