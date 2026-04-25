package uz.java.dto.vacancy;

import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;
import uz.java.entity.enums.CurrencyType;
import uz.java.entity.enums.EmploymentType;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class VacancyResponse {

    String name;
    String description;
    Long companyId;
    Integer salary;
    CurrencyType currency;
    EmploymentType employmentType;
    String experience;
    String city;
    String status;
    String location;
    String contactName;
}
