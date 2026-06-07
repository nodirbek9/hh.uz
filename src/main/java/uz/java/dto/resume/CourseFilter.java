package uz.java.dto.resume;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;
import uz.java.dto.BaseFilter;

import java.util.List;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
@AllArgsConstructor
@NoArgsConstructor
public class CourseFilter extends BaseFilter {
    List<Long> resumeId;
    String name;
    String organization;
    String specialisation;
    String graduationYear;

    public CourseFilter(Integer page,
                        Integer limit,
                        String sortBy,
                        List<Long> resumeId,
                        String name,
                        String organization,
                        String specialisation,
                        String graduationYear
    ) {
        super(page, limit, sortBy);
        this.resumeId = resumeId;
        this.name = name;
        this.organization = organization;
        this.specialisation = specialisation;
        this.graduationYear = graduationYear;
    }
}
