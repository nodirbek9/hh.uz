package uz.java.dto.resume;

import lombok.Data;

@Data
public class CourseResponse {

    private Long id;

    private Long resumeId;

    private String name;

    private String organization;

    private String specialisation;

    private String graduationYear;
}
