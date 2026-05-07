package uz.java.mapper;

import org.mapstruct.Mapper;
import uz.java.dto.resume.ProfessionResponse;
import uz.java.entity.employer.Profession;

@Mapper(componentModel = "spring")
public interface ProfessionMapper {
    ProfessionResponse toResponse(Profession profession);
}
