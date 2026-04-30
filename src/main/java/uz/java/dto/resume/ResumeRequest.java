package uz.java.dto.resume;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

import java.util.*;
@Getter
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ResumeRequest {
    @NotNull(message = "profession.must.not.ne.null")
    @NotBlank(message = "profession.must.not.be.blank")
    String professional;
    String status;
    String aboutMe;
    List<Long> certificateIds = new ArrayList<>();
    Set<Long> skillIds = new HashSet<>();

}
