package uz.java.dto.resume;

import jakarta.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;
import uz.java.entity.enums.ResumeCreationType;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ResumeRequest {
    @NotNull(message = "profession.id.must.not.be.null")
    private Long professionId;
    @NotNull(message = "profession.id.must.not.be.null")
    private List<Long> educationIds = new ArrayList<>();
    private List<SkillDto> skillIds = new ArrayList<>();
    private List<WorkExperienceDto> workExperienceIds = new ArrayList<>();
    private ResumeCreationType type;
    private String photoPath;

    @Data
    public static class SkillDto {
        private Long id;
        private String skillLevel;
    }

    @Data
    public static class WorkExperienceDto {
        private Long id;
        private String companyName;
        private LocalDate startDate;
        private LocalDate endDate;
        private boolean isCurrent;
        private String description;
    }
}
