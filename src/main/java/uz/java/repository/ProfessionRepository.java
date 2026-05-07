package uz.java.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import uz.java.entity.employer.Profession;

import java.util.List;

public interface ProfessionRepository extends JpaRepository<Profession, Long> {
    List<Profession> findByParentId(Long parentId);
}
