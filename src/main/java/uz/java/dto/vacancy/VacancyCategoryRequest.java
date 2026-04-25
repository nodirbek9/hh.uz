package uz.java.dto.vacancy;

import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class VacancyCategoryRequest {
    String name;

    String parentId;

    String iconUrl;
}
