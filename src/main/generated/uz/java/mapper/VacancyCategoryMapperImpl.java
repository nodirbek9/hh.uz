package uz.java.mapper;

import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;
import uz.java.dto.vacancy.VacancyCategoryRequest;
import uz.java.dto.vacancy.VacancyCategoryResponse;
import uz.java.entity.employer.Specializations;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2026-06-10T21:35:54+0300",
    comments = "version: 1.5.5.Final, compiler: javac, environment: Java 21.0.8 (Amazon.com Inc.)"
)
@Component
public class VacancyCategoryMapperImpl implements VacancyCategoryMapper {

    @Override
    public VacancyCategoryResponse toVacancyCategoryResponse(Specializations specializations) {
        if ( specializations == null ) {
            return null;
        }

        VacancyCategoryResponse vacancyCategoryResponse = new VacancyCategoryResponse();

        vacancyCategoryResponse.setId( specializations.getId() );
        vacancyCategoryResponse.setName( specializations.getName() );
        if ( specializations.getParentId() != null ) {
            vacancyCategoryResponse.setParentId( String.valueOf( specializations.getParentId() ) );
        }

        return vacancyCategoryResponse;
    }

    @Override
    public Specializations toVacancyCategory(VacancyCategoryRequest request) {
        if ( request == null ) {
            return null;
        }

        Specializations specializations = new Specializations();

        specializations.setName( request.getName() );
        if ( request.getParentId() != null ) {
            specializations.setParentId( Long.parseLong( request.getParentId() ) );
        }

        return specializations;
    }

    @Override
    public void updateVacancyCategory(VacancyCategoryRequest request, Specializations oldVacancyCategory) {
        if ( request == null ) {
            return;
        }

        if ( request.getName() != null ) {
            oldVacancyCategory.setName( request.getName() );
        }
        if ( request.getParentId() != null ) {
            oldVacancyCategory.setParentId( Long.parseLong( request.getParentId() ) );
        }
    }
}
