package uz.java.mapper;

import org.mapstruct.*;
import uz.java.dto.company.CompanyRequest;
import uz.java.dto.company.CompanyResponse;
import uz.java.entity.employer.Company;

@Mapper(componentModel = "spring")
public interface CompanyMapper {

    @Mapping(source = "user.id", target = "ownerId")
    CompanyResponse toResponse(Company company);

    @Mapping(source = "phoneNumber", target = "phone")
    Company toCompany(CompanyRequest request);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateFromRequest(CompanyRequest request, @MappingTarget Company company);
}
