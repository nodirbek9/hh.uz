package uz.java.mapper;

import org.mapstruct.*;
import uz.java.dto.vacancy.VacancyRequest;
import uz.java.dto.vacancy.VacancyResponse;
import uz.java.entity.employer.Vacancy;

@Mapper(componentModel = "spring")
public interface VacancyMapper {

    @Mapping(source = "company.id", target = "companyId")
    VacancyResponse toVacancyResponse(Vacancy vacancy);

    @Mapping(source = "companyId", target = "company.id")
    Vacancy toVacancy(VacancyRequest request);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateFromRequest(VacancyRequest request, @MappingTarget Vacancy vacancy);
}
