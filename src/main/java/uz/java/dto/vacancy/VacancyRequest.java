package uz.java.dto.vacancy;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class VacancyRequest {

    @NotNull(message = "vacancy.company.must.not.be.null")
    Long companyId;
    String employmentType;
    String experience;
    String status;
    String contactName;
    @NotNull(message = "vacancy.name.must.not.be.null")
    @NotBlank(message = "vacancy.name.must.not.be.blank")
    String name;
    String description;
    String location;
    String city;
    Integer salary;
    String currency;
    Long specializationId;
}
