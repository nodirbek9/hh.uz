package uz.java.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public abstract class BaseFilter {
    private Integer page;
    private Integer limit;
    private String sortBy;
}
