package uz.java.repository;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import uz.java.dto.resume.CertificateShortResponse;
import uz.java.entity.jobseeker.Certificate;

import java.util.List;

public interface CertificateRepository extends JpaRepository<Certificate, Long>, JpaSpecificationExecutor<Certificate> {
    @Query("select c from Certificate c where c.name=:name")
    Certificate findByNameCustom(String name);

    @Query("select new uz.java.dto.resume.CertificateShortResponse(t.id, t.name, t.receivedFrom," +
            " t.receivedAt, t.contactName, t.issueAt) from Certificate t where lower(t.name) ilike lower(concat('%', :name, '%')) ")
//    @Query("select c from Certificate c where c.name=:name")
    List<CertificateShortResponse> findAllCustom(@Param("name") String name, Pageable pageable);
}
