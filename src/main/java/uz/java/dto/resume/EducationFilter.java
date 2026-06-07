package uz.java.dto.resume;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import uz.java.dto.BaseFilter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class EducationFilter extends BaseFilter {
    private String name;

    public EducationFilter(Integer page, Integer limit, String sortBy, String name) {
        super(page, limit, sortBy);
        this.name = name;
    }
}
