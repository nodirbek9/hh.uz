package uz.java.mapper;

import org.mapstruct.*;
import uz.java.dto.vacancy.VacancyRequest;
import uz.java.dto.vacancy.VacancyResponse;
import uz.java.entity.employer.Vacancy;

@Mapper(componentModel = "spring")
public interface VacancyMapper {

    @Mapping(source = "company.id", target = "companyId")
    @Mapping(source = "specialization.id", target = "specializationId")
    @Mapping(source = "specialization.name", target = "specializationName")
    VacancyResponse toVacancyResponse(Vacancy vacancy);

    @Mapping(source = "companyId", target = "company.id")
    @Mapping(source = "specializationId", target = "specialization.id")
    Vacancy toVacancy(VacancyRequest request);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateFromRequest(VacancyRequest request, @MappingTarget Vacancy vacancy);
}
