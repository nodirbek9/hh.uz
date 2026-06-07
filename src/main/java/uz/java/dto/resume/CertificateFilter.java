package uz.java.dto.resume;

import lombok.AllArgsConstructor;
import lombok.Data;
import uz.java.dto.BaseFilter;

@Data
@AllArgsConstructor
public class CertificateFilter extends BaseFilter {
    private String name;

    public CertificateFilter(Integer page, Integer limit, String sortBy, String name) {
        super(page, limit, sortBy);
        this.name = name;
    }
}
