package uz.java.mapper;

import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;
import uz.java.dto.vacancy.VacancyCategoryRequest;
import uz.java.dto.vacancy.VacancyCategoryResponse;
import uz.java.entity.employer.Specializations;
import uz.java.entity.employer.VacancyCategory;

@Mapper(componentModel = "spring")
public interface VacancyCategoryMapper {


    VacancyCategoryResponse toVacancyCategoryResponse(Specializations specializations);

    Specializations toVacancyCategory(VacancyCategoryRequest request);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateVacancyCategory(VacancyCategoryRequest request, @MappingTarget Specializations oldVacancyCategory);
}
