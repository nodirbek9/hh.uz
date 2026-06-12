package uz.java.entity.jobseeker;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import uz.java.entity.user.Auditable;

import java.time.LocalDate;

@AllArgsConstructor
@NoArgsConstructor
@Entity
@Getter
@Setter
@Table(name = "certificates")
public class Certificate extends Auditable {

    private String name;

    private String receivedFrom;

    private LocalDate receivedAt;

    private String contactName;

    private LocalDate issueAt;

    @Column(name = "file_path", length = 500)
    private String filePath;

    @ManyToOne
    private Resume resume;
}