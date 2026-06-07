package uz.java.dto.resume;

import lombok.Data;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Data
public class ResumeResponse {
    Long id;
    String profession;
    String status;
    String aboutMe;
    List<CertificateShortResponse> certificateList = new ArrayList<>();
    Set<SkillResponse> skills = new HashSet<>();
    List<CourseResponse> courseList = new ArrayList<>();
    List<PortfolioResponse> portfolioList = new ArrayList<>();
}
