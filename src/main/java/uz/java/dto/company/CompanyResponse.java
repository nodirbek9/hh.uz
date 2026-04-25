package uz.java.dto.company;

import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

import java.util.List;
// response ga faqat kerakli field lar yuboriladi buni Front taraf bilan kelishiladi
@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class CompanyResponse {
    Long id;
    String name;
    Long ownerId;
    String description;
    String logo;
    String country;
    String city;
    String phone;
    String email;
    List<String> imageUrls;
}
