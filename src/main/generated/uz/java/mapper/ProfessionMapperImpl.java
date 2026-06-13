package uz.java.mapper;

import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;
import uz.java.dto.resume.ProfessionResponse;
import uz.java.entity.employer.Profession;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2026-06-10T21:35:54+0300",
    comments = "version: 1.5.5.Final, compiler: javac, environment: Java 21.0.8 (Amazon.com Inc.)"
)
@Component
public class ProfessionMapperImpl implements ProfessionMapper {

    @Override
    public ProfessionResponse toResponse(Profession profession) {
        if ( profession == null ) {
            return null;
        }

        ProfessionResponse professionResponse = new ProfessionResponse();

        professionResponse.setId( profession.getId() );
        professionResponse.setName( profession.getName() );

        return professionResponse;
    }
}
