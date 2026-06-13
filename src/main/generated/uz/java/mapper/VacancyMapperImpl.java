package uz.java.mapper;

import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;
import uz.java.dto.vacancy.VacancyRequest;
import uz.java.dto.vacancy.VacancyResponse;
import uz.java.entity.employer.Company;
import uz.java.entity.employer.Specializations;
import uz.java.entity.employer.Vacancy;
import uz.java.entity.enums.CurrencyType;
import uz.java.entity.enums.EmploymentType;
import uz.java.entity.enums.Experience;
import uz.java.entity.enums.StatusVacancy;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2026-06-10T21:35:53+0300",
    comments = "version: 1.5.5.Final, compiler: javac, environment: Java 21.0.8 (Amazon.com Inc.)"
)
@Component
public class VacancyMapperImpl implements VacancyMapper {

    @Override
    public VacancyResponse toVacancyResponse(Vacancy vacancy) {
        if ( vacancy == null ) {
            return null;
        }

        VacancyResponse vacancyResponse = new VacancyResponse();

        vacancyResponse.setCompanyId( vacancyCompanyId( vacancy ) );
        vacancyResponse.setSpecializationId( vacancySpecializationId( vacancy ) );
        vacancyResponse.setSpecializationName( vacancySpecializationName( vacancy ) );
        vacancyResponse.setName( vacancy.getName() );
        vacancyResponse.setDescription( vacancy.getDescription() );
        vacancyResponse.setSalary( vacancy.getSalary() );
        if ( vacancy.getCurrency() != null ) {
            vacancyResponse.setCurrency( vacancy.getCurrency().name() );
        }
        vacancyResponse.setEmploymentType( vacancy.getEmploymentType() );
        if ( vacancy.getExperience() != null ) {
            vacancyResponse.setExperience( vacancy.getExperience().name() );
        }
        if ( vacancy.getStatus() != null ) {
            vacancyResponse.setStatus( vacancy.getStatus().name() );
        }
        vacancyResponse.setLocation( vacancy.getLocation() );
        vacancyResponse.setContactName( vacancy.getContactName() );

        return vacancyResponse;
    }

    @Override
    public Vacancy toVacancy(VacancyRequest request) {
        if ( request == null ) {
            return null;
        }

        Vacancy vacancy = new Vacancy();

        vacancy.setCompany( vacancyRequestToCompany( request ) );
        vacancy.setSpecialization( vacancyRequestToSpecializations( request ) );
        vacancy.setName( request.getName() );
        vacancy.setDescription( request.getDescription() );
        vacancy.setSalary( request.getSalary() );
        if ( request.getCurrency() != null ) {
            vacancy.setCurrency( Enum.valueOf( CurrencyType.class, request.getCurrency() ) );
        }
        if ( request.getEmploymentType() != null ) {
            vacancy.setEmploymentType( Enum.valueOf( EmploymentType.class, request.getEmploymentType() ) );
        }
        if ( request.getExperience() != null ) {
            vacancy.setExperience( Enum.valueOf( Experience.class, request.getExperience() ) );
        }
        if ( request.getStatus() != null ) {
            vacancy.setStatus( Enum.valueOf( StatusVacancy.class, request.getStatus() ) );
        }
        vacancy.setLocation( request.getLocation() );
        vacancy.setContactName( request.getContactName() );

        return vacancy;
    }

    @Override
    public void updateFromRequest(VacancyRequest request, Vacancy vacancy) {
        if ( request == null ) {
            return;
        }

        if ( request.getName() != null ) {
            vacancy.setName( request.getName() );
        }
        if ( request.getDescription() != null ) {
            vacancy.setDescription( request.getDescription() );
        }
        if ( request.getSalary() != null ) {
            vacancy.setSalary( request.getSalary() );
        }
        if ( request.getCurrency() != null ) {
            vacancy.setCurrency( Enum.valueOf( CurrencyType.class, request.getCurrency() ) );
        }
        if ( request.getEmploymentType() != null ) {
            vacancy.setEmploymentType( Enum.valueOf( EmploymentType.class, request.getEmploymentType() ) );
        }
        if ( request.getExperience() != null ) {
            vacancy.setExperience( Enum.valueOf( Experience.class, request.getExperience() ) );
        }
        if ( request.getStatus() != null ) {
            vacancy.setStatus( Enum.valueOf( StatusVacancy.class, request.getStatus() ) );
        }
        if ( request.getLocation() != null ) {
            vacancy.setLocation( request.getLocation() );
        }
        if ( request.getContactName() != null ) {
            vacancy.setContactName( request.getContactName() );
        }
    }

    private Long vacancyCompanyId(Vacancy vacancy) {
        if ( vacancy == null ) {
            return null;
        }
        Company company = vacancy.getCompany();
        if ( company == null ) {
            return null;
        }
        Long id = company.getId();
        if ( id == null ) {
            return null;
        }
        return id;
    }

    private Long vacancySpecializationId(Vacancy vacancy) {
        if ( vacancy == null ) {
            return null;
        }
        Specializations specialization = vacancy.getSpecialization();
        if ( specialization == null ) {
            return null;
        }
        Long id = specialization.getId();
        if ( id == null ) {
            return null;
        }
        return id;
    }

    private String vacancySpecializationName(Vacancy vacancy) {
        if ( vacancy == null ) {
            return null;
        }
        Specializations specialization = vacancy.getSpecialization();
        if ( specialization == null ) {
            return null;
        }
        String name = specialization.getName();
        if ( name == null ) {
            return null;
        }
        return name;
    }

    protected Company vacancyRequestToCompany(VacancyRequest vacancyRequest) {
        if ( vacancyRequest == null ) {
            return null;
        }

        Company company = new Company();

        company.setId( vacancyRequest.getCompanyId() );

        return company;
    }

    protected Specializations vacancyRequestToSpecializations(VacancyRequest vacancyRequest) {
        if ( vacancyRequest == null ) {
            return null;
        }

        Specializations specializations = new Specializations();

        specializations.setId( vacancyRequest.getSpecializationId() );

        return specializations;
    }
}
