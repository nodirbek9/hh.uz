package uz.java.dto.company;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;
import uz.java.dto.BaseFilter;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
@AllArgsConstructor
@NoArgsConstructor
public class CompanyFilter extends BaseFilter {

    String name;
    String description;
    String country;
    String city;
    String phone;
    String email;

    public CompanyFilter(
            Integer page,
            Integer limit,
            String sortBy,
            String name,
            String description,
            String country,
            String city,
            String phone,
            String email
    ) {
        super(page, limit, sortBy);
        this.name = name;
        this.description = description;
        this.country = country;
        this.city = city;
        this.phone = phone;
        this.email = email;
    }
}
