package uz.java.dto.resume;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class SkillResponse {
    private Long id;
    private String name;
    private String code;
}
