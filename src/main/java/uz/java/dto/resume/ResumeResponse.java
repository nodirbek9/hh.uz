package uz.java.dto.resume;

import lombok.Data;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Data
public class ResumeResponse {

    String id;
    String name;
    String profession;
    String status;
    String aboutMe;
    List<CertificateShortResponse> certificateList = new ArrayList<>();
    Set<SkillResponse> skills = new HashSet<>();

    @Data
    public class CertificateShortResponse {
        private Long id;

        private String name;

        private String receivedFrom;

        private LocalDate receivedAt;

        private String contactName;

        private LocalDate issueAt;
    }

    @Data
    public class SkillResponse {
        private Long id;
        private String name;
        private String code;
    }

}
