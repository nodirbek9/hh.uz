package uz.java.dto.resume;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class CourseRequest {

    @NotNull(message = "resume.id.must.not.be.null")
    private Long resumeId;

    private String name;

    private String organization;

    private String specialisation;

    private String graduationYear;
}
