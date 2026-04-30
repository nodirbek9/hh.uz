package uz.java.mapper;

import org.mapstruct.Mapper;
import uz.java.dto.resume.ResumeResponse;
import uz.java.entity.jobseeker.Certificate;

@Mapper(componentModel = "spring")
public interface CertificateMapper {
    ResumeResponse.CertificateShortResponse toStringResponse(Certificate certificate);

}
