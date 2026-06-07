package uz.java.specifications;

import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import org.springframework.data.jpa.domain.Specification;
import uz.java.dto.vacancy.VacancyFilter;
import uz.java.entity.employer.Vacancy;

import java.util.ArrayList;
import java.util.List;

public record VacancySpecification(VacancyFilter filter) implements Specification<Vacancy> {
    @Override
    public Predicate toPredicate(Root<Vacancy> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
        List<Predicate> predicates = new ArrayList<>();
        if (filter.getSearch() != null) {
            predicates.add(criteriaBuilder.like(criteriaBuilder.upper(root.get("name")), "%" +
                    filter.getSearch().toUpperCase() + "%"));
        }
        predicates.add(criteriaBuilder.isFalse(root.get("deleted")));

        if (filter.getSalaryFrom() != null)
            predicates.add(criteriaBuilder.greaterThanOrEqualTo(root.get("salary"), filter.getSalaryFrom()));

        if (filter.getWorkFormat() != null)
            predicates.add(root.get("workFormat").in(filter.getWorkFormat()));

        if (filter.getEmploymentType() != null)
            predicates.add(root.get("employmentType").in(filter.getEmploymentType()));

        if (filter.getExperience() != null)
            predicates.add(root.get("experience").in(filter.getExperience()));

        if (filter.getWorkTimeSlot() != null)
            predicates.add(root.get("workTimeSlot").in(filter.getWorkTimeSlot()));;

        if (filter.getCompanyIndustryIds() != null)
            predicates.add(root.get("companyIndustryIds").in(filter.getCompanyIndustryIds()));

        if (filter.getCountryIds() != null)
            predicates.add(root.get("countryIds").in(filter.getCountryIds()));

        if (filter.getCityIds() != null)
            predicates.add(root.get("cityIds").in(filter.getCityIds()));

        if (filter.getSalaryFrequency() != null)
            predicates.add(root.get("salaryFrequency").in(filter.getSalaryFrequency()));

        if (filter.getVocational() != null)
            predicates.add(root.get("vocational").in(filter.getVocational()));

        if (filter.getWorkingHours() != null)
            predicates.add(root.get("workingHours").in(filter.getWorkingHours()));

        if (filter.getVacancyDisplay() != null)
            predicates.add(root.get("vacancyDisplay").in(filter.getVacancyDisplay()));

        if (filter.getSpecializationId() != null)
            predicates.add(criteriaBuilder.equal(
                    root.get("specialization").get("id"), filter.getSpecializationId()
            ));

        return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
    }
}
