package uz.java.dto.vacancy;

import lombok.*;
import lombok.experimental.FieldDefaults;
import uz.java.dto.BaseFilter;

import java.util.List;
@Getter
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE)
@AllArgsConstructor
@NoArgsConstructor
public class VacancyFilter extends BaseFilter {
    String search;
    Integer salaryFrom;
    String currencyType;
    List<String> employmentType;
    List<String> experience;
    List<String> workTimeSlot;
    List<String> workFormat;
    List<Long> companyIndustryIds;
    List<Long> countryIds;
    List<Long> cityIds;
    List<String> salaryFrequency;
    List<String> vocational;
    List<String> workingHours;
    List<String> vacancyDisplay;

    public VacancyFilter(Integer page, Integer limit,
                         String sortBy, String search,
                         Integer salaryFrom, String currencyType,
                         List<String> employmentType, List<String> experience,
                         List<String> workTimeSlot, List<String> workFormat,
                         List<Long> companyIndustryIds, List<Long> countryIds,
                         List<Long> cityIds, List<String> salaryFrequency,
                         List<String> vocational, List<String> workingHours,
                         List<String> vacancyDisplay) {
        super(page, limit, sortBy);
        this.search = search;
        this.salaryFrom = salaryFrom;
        this.currencyType = currencyType;
        this.employmentType = employmentType;
        this.experience = experience;
        this.workTimeSlot = workTimeSlot;
        this.workFormat = workFormat;
        this.companyIndustryIds = companyIndustryIds;
        this.countryIds = countryIds;
        this.cityIds = cityIds;
        this.salaryFrequency = salaryFrequency;
        this.vocational = vocational;
        this.workingHours = workingHours;
        this.vacancyDisplay = vacancyDisplay;
    }
}
