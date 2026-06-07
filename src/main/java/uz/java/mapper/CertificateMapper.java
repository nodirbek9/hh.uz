package uz.java.mapper;

import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;
import uz.java.dto.resume.CertificateRequest;
import uz.java.dto.resume.CertificateShortResponse;
import uz.java.entity.jobseeker.Certificate;

@Mapper(componentModel = "spring")
public interface CertificateMapper {
    CertificateShortResponse toShortResponse(Certificate certificate);

    Certificate toEntity(CertificateRequest request);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateFromRequest(CertificateRequest request, @MappingTarget Certificate oldCertificate);
}
