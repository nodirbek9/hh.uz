package uz.java.dto.application;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class ApplicationResponse {
    private Long id;
    private Long vacancyId;
    private String vacancyName;
    private Long userId;
    private Long resumeId;
    private String letter;
    private String status;
    private LocalDateTime createdAt;
}
