package uz.java.dto.vacancy;

import jakarta.validation.constraints.NotBlank;
import lombok.AccessLevel;
import lombok.Data;
import jakarta.validation.constraints.NotNull;
import lombok.experimental.FieldDefaults;
import uz.java.entity.enums.CurrencyType;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class VacancyRequest {
    @NotNull(message = "name must not be null")
    @NotBlank(message = "name must not be blank")
    Long companyId;
    String employmentType;
    String experience;
    String status;
    String contactName;
    String name;
    String description;
    String location;
    String city;
    Integer salary;
    String currency;
}
