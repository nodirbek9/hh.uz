package uz.java.mapper;

import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;
import uz.java.dto.resume.EducationRequest;
import uz.java.dto.resume.EducationResponse;
import uz.java.entity.enums.EducationDegree;
import uz.java.entity.jobseeker.Education;
import uz.java.entity.user.UserProfile;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2026-06-10T21:35:54+0300",
    comments = "version: 1.5.5.Final, compiler: javac, environment: Java 21.0.8 (Amazon.com Inc.)"
)
@Component
public class EducationMapperImpl implements EducationMapper {

    @Override
    public EducationResponse toResponse(Education education) {
        if ( education == null ) {
            return null;
        }

        EducationResponse educationResponse = new EducationResponse();

        educationResponse.setUserProfileId( educationUserProfileId( education ) );
        educationResponse.setId( education.getId() );
        educationResponse.setName( education.getName() );
        if ( education.getDegree() != null ) {
            educationResponse.setDegree( education.getDegree().name() );
        }
        educationResponse.setFacultyName( education.getFacultyName() );
        educationResponse.setSpeciality( education.getSpeciality() );
        educationResponse.setStartDate( education.getStartDate() );
        educationResponse.setEndDate( education.getEndDate() );

        return educationResponse;
    }

    @Override
    public Education toEntity(EducationRequest educationRequest) {
        if ( educationRequest == null ) {
            return null;
        }

        Education education = new Education();

        education.setUserProfile( educationRequestToUserProfile( educationRequest ) );
        education.setName( educationRequest.getName() );
        if ( educationRequest.getDegree() != null ) {
            education.setDegree( Enum.valueOf( EducationDegree.class, educationRequest.getDegree() ) );
        }
        education.setFacultyName( educationRequest.getFacultyName() );
        education.setSpeciality( educationRequest.getSpeciality() );
        education.setEndDate( educationRequest.getEndDate() );

        return education;
    }

    @Override
    public void updateFromRequest(EducationRequest request, Education education) {
        if ( request == null ) {
            return;
        }

        if ( request.getName() != null ) {
            education.setName( request.getName() );
        }
        if ( request.getDegree() != null ) {
            education.setDegree( Enum.valueOf( EducationDegree.class, request.getDegree() ) );
        }
        if ( request.getFacultyName() != null ) {
            education.setFacultyName( request.getFacultyName() );
        }
        if ( request.getSpeciality() != null ) {
            education.setSpeciality( request.getSpeciality() );
        }
        if ( request.getEndDate() != null ) {
            education.setEndDate( request.getEndDate() );
        }
    }

    private Long educationUserProfileId(Education education) {
        if ( education == null ) {
            return null;
        }
        UserProfile userProfile = education.getUserProfile();
        if ( userProfile == null ) {
            return null;
        }
        Long id = userProfile.getId();
        if ( id == null ) {
            return null;
        }
        return id;
    }

    protected UserProfile educationRequestToUserProfile(EducationRequest educationRequest) {
        if ( educationRequest == null ) {
            return null;
        }

        UserProfile userProfile = new UserProfile();

        userProfile.setId( educationRequest.getUserProfileId() );

        return userProfile;
    }
}
