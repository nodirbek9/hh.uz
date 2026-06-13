package uz.java.mapper;

import java.time.LocalDate;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;
import uz.java.dto.resume.CertificateRequest;
import uz.java.dto.resume.CertificateShortResponse;
import uz.java.entity.jobseeker.Certificate;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2026-06-10T21:35:53+0300",
    comments = "version: 1.5.5.Final, compiler: javac, environment: Java 21.0.8 (Amazon.com Inc.)"
)
@Component
public class CertificateMapperImpl implements CertificateMapper {

    @Override
    public CertificateShortResponse toShortResponse(Certificate certificate) {
        if ( certificate == null ) {
            return null;
        }

        Long id = null;
        String name = null;
        String receivedFrom = null;
        LocalDate receivedAt = null;
        String contactName = null;
        LocalDate issueAt = null;

        id = certificate.getId();
        name = certificate.getName();
        receivedFrom = certificate.getReceivedFrom();
        receivedAt = certificate.getReceivedAt();
        contactName = certificate.getContactName();
        issueAt = certificate.getIssueAt();

        CertificateShortResponse certificateShortResponse = new CertificateShortResponse( id, name, receivedFrom, receivedAt, contactName, issueAt );

        return certificateShortResponse;
    }

    @Override
    public Certificate toEntity(CertificateRequest request) {
        if ( request == null ) {
            return null;
        }

        Certificate certificate = new Certificate();

        certificate.setName( request.getName() );
        certificate.setReceivedFrom( request.getReceivedFrom() );
        certificate.setContactName( request.getContactName() );

        return certificate;
    }

    @Override
    public void updateFromRequest(CertificateRequest request, Certificate oldCertificate) {
        if ( request == null ) {
            return;
        }

        if ( request.getName() != null ) {
            oldCertificate.setName( request.getName() );
        }
        if ( request.getReceivedFrom() != null ) {
            oldCertificate.setReceivedFrom( request.getReceivedFrom() );
        }
        if ( request.getContactName() != null ) {
            oldCertificate.setContactName( request.getContactName() );
        }
    }
}
