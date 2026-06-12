package uz.java.dto.resume;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class PortfolioRequest {
    @NotNull
    private Long resumeId;
    @NotBlank
    private String image;

    private String fileUrl;
}
