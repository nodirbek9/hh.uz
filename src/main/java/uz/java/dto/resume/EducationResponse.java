package uz.java.dto.resume;

import lombok.Data;

@Data
public class EducationResponse {

    private Long id;

    private Long userProfileId;

    private String name;

    private String degree;

    private String facultyName;

    private String speciality;

    private String startDate;

    private String endDate;
}
