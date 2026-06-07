package uz.java.dto.application;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class ApplicationRequest {
    @NotNull
    private Long vacancyId;
    @NotNull
    private Long resumeId;
    private String letter;
}
