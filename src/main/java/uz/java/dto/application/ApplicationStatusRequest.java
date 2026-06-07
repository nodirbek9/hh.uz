package uz.java.dto.application;

import jakarta.validation.constraints.NotNull;
import lombok.Data;
import uz.java.entity.enums.VacancyApplicationStatus;

@Data
public class ApplicationStatusRequest {
    @NotNull
    private VacancyApplicationStatus status;
    private String comment;
}
