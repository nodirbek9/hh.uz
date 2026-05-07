package uz.java.dto.resume;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class SkillRequest {

    @NotNull(message = "skill.name.must.not.be.null")
    private String name;

    private String skillLevel;
}