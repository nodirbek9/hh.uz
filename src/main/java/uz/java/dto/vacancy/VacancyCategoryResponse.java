package uz.java.dto.vacancy;

import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class VacancyCategoryResponse {
    Long id;

    String name;

    String parentId;

    String iconUrl;

}
