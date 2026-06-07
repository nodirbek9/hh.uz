package uz.java.specifications;

import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import org.springframework.data.jpa.domain.Specification;
import uz.java.dto.company.CompanyFilter;
import uz.java.entity.employer.Company;

import java.util.ArrayList;
import java.util.List;

public record CompanySpecification(CompanyFilter filter) implements Specification<Company> {

    @Override
    public Predicate toPredicate(Root<Company> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
        List<Predicate> predicates = new ArrayList<>();
        if (filter.getName() != null) {
            predicates.add(criteriaBuilder.like(criteriaBuilder.upper(root.get("name")), "%" +
                    filter.getName().toUpperCase() + "%"));
        }
        if (filter.getDescription() != null) {
            predicates.add(criteriaBuilder.like(criteriaBuilder.upper(root.get("description")), "%" +
                    filter.getDescription().toUpperCase() + "%"));
        }
        if (filter.getCountry() != null) {
            predicates.add(criteriaBuilder.like(criteriaBuilder.upper(root.get("country")), "%" +
                    filter.getCountry().toUpperCase() + "%"));
        }
        if (filter.getCity() != null) {
            predicates.add(criteriaBuilder.like(criteriaBuilder.upper(root.get("city")), "%" +
                    filter.getCity().toUpperCase() + "%"));
        }
        if (filter.getPhone() != null) {
            predicates.add(criteriaBuilder.like(criteriaBuilder.upper(root.get("phone")), "%" +
                    filter.getPhone().toUpperCase() + "%"));
        }
        if (filter.getEmail() != null) {
            predicates.add(criteriaBuilder.like(criteriaBuilder.upper(root.get("email")), "%" +
                    filter.getEmail().toUpperCase() + "%"));
        }
        return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
    }
}
