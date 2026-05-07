package uz.java.dto.user;

import lombok.Data;

@Data
public class WorkExperienceResponse {

    private String companyName;

    private String jobTitle;

    private String description;

    private String startDate;

    private String endDate;

    private Boolean isCurrent;
}
