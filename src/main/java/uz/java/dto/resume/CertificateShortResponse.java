package uz.java.dto.resume;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDate;
@Data
@AllArgsConstructor
public class CertificateShortResponse {
    private Long id;

    private String name;

    private String receivedFrom;

    private LocalDate receivedAt;

    private String contactName;

    private LocalDate issueAt;
}
