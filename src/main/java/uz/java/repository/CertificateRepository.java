package uz.java.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import uz.java.entity.jobseeker.Certificate;

public interface CertificateRepository extends JpaRepository<Certificate, Long>, JpaSpecificationExecutor<Certificate> {
    @Query("select c from Certificate c where c.name=:name")
    Certificate findByNameCustom(String name);
}
