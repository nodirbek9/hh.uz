package uz.java.dto.resume;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class CertificateRequest {

    @NotNull(message = "certificate.name.must.not.be.null")
    private String name;

    @NotNull(message = "received.from.certiface.must.not.be.null")
    private String receivedFrom;

    private String contactName;

    @NotNull(message = "resume.id.must.not.be.null")
    private Long resumeId;
}
