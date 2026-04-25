package uz.java.entity.jobseeker;

import jakarta.persistence.*;
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

    @ManyToOne
    @JoinColumn(name = "resume_id")
    private Resume resume;
}