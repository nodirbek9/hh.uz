package uz.java.dto.company;

import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class CompanyRequest {
     String name;
     String description;
     String country;
     String city;
     String phoneNumber;
     String email;
}
