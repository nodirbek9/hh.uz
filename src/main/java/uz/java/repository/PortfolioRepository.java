package uz.java.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import uz.java.entity.jobseeker.Portfolio;

import java.util.List;

public interface PortfolioRepository extends JpaRepository<Portfolio, Long> {
    List<Portfolio> findByResumeId(Long resumeId);
}
