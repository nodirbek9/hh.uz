package uz.java.dto.resume;

import lombok.AllArgsConstructor;
import lombok.Data;
import uz.java.entity.enums.EducationDegree;

@Data
@AllArgsConstructor
public class EducationShortResponse {
    private Long id;
    private String name;
    private EducationDegree degree;
}
