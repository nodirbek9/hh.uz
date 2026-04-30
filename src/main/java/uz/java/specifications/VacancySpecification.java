package uz.java.specifications;

import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import org.jspecify.annotations.Nullable;
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
        if (filter.getSalaryFrom() != null) {
            predicates.add(criteriaBuilder.greaterThanOrEqualTo(root.get("salary"), filter.getSalaryFrom()));
        }
        return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
    }
}
