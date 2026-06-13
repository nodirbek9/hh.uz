package uz.java.mapper;

import java.time.format.DateTimeFormatter;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;
import uz.java.dto.resume.ResumeRequest;
import uz.java.dto.user.WorkExperienceResponse;
import uz.java.entity.jobseeker.WorkExperience;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2026-06-10T21:35:54+0300",
    comments = "version: 1.5.5.Final, compiler: javac, environment: Java 21.0.8 (Amazon.com Inc.)"
)
@Component
public class WorkExperienceMapperImpl implements WorkExperienceMapper {

    @Override
    public WorkExperience toEntity(ResumeRequest.WorkExperienceDto workExperienceDto) {
        if ( workExperienceDto == null ) {
            return null;
        }

        WorkExperience workExperience = new WorkExperience();

        workExperience.setId( workExperienceDto.getId() );
        workExperience.setCompanyName( workExperienceDto.getCompanyName() );
        workExperience.setDescription( workExperienceDto.getDescription() );
        workExperience.setStartDate( workExperienceDto.getStartDate() );
        workExperience.setEndDate( workExperienceDto.getEndDate() );

        return workExperience;
    }

    @Override
    public WorkExperienceResponse toResponse(WorkExperience workExperience) {
        if ( workExperience == null ) {
            return null;
        }

        WorkExperienceResponse workExperienceResponse = new WorkExperienceResponse();

        workExperienceResponse.setCompanyName( workExperience.getCompanyName() );
        workExperienceResponse.setJobTitle( workExperience.getJobTitle() );
        workExperienceResponse.setDescription( workExperience.getDescription() );
        if ( workExperience.getStartDate() != null ) {
            workExperienceResponse.setStartDate( DateTimeFormatter.ISO_LOCAL_DATE.format( workExperience.getStartDate() ) );
        }
        if ( workExperience.getEndDate() != null ) {
            workExperienceResponse.setEndDate( DateTimeFormatter.ISO_LOCAL_DATE.format( workExperience.getEndDate() ) );
        }
        workExperienceResponse.setIsCurrent( workExperience.getIsCurrent() );

        return workExperienceResponse;
    }
}
