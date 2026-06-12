package uz.java.dto.resume;

import lombok.Data;

@Data
public class PortfolioResponse {
    private Long id;
    private Long resumeId;
    private String image;
    private String title;
    private String description;
    private String fileUrl;
}
