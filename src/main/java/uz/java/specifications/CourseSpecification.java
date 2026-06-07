package uz.java.specifications;

import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import org.springframework.data.jpa.domain.Specification;
import uz.java.dto.resume.CourseFilter;
import uz.java.entity.jobseeker.Course;

import java.util.ArrayList;
import java.util.List;

public record CourseSpecification(CourseFilter filter) implements Specification<Course> {

    @Override
    public Predicate toPredicate(Root<Course> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
        List<Predicate> predicates = new ArrayList<>();
        if (filter.getResumeId() != null) {
            predicates.add(root.get("resume").get("id").in(filter.getResumeId()));
        }
        if (filter.getName() != null) {
            predicates.add(criteriaBuilder.like(criteriaBuilder.upper(root.get("name")), "%" +
                    filter.getName().toUpperCase() + "%"));
        }
        if (filter.getOrganization() != null) {
            predicates.add(criteriaBuilder.like(criteriaBuilder.upper(root.get("organization")), "%" +
                    filter.getOrganization().toUpperCase() + "%"));
        }
        if (filter.getSpecialisation() != null) {
            predicates.add(criteriaBuilder.like(criteriaBuilder.upper(root.get("specialisation")), "%" +
                    filter.getSpecialisation().toUpperCase() + "%"));
        }
        if (filter.getGraduationYear() != null) {
            predicates.add(criteriaBuilder.like(criteriaBuilder.upper(root.get("graduationYear")), "%" +
                    filter.getGraduationYear().toUpperCase() + "%"));
        }
        return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
    }
}
