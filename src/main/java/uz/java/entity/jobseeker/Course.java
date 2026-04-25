package uz.java.entity.jobseeker;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import uz.java.entity.user.Auditable;

@AllArgsConstructor
@NoArgsConstructor
@Entity
@Getter
@Setter
@Table(name = "courses")
public class Course extends Auditable {

    @ManyToOne
    @JoinColumn(name = "resume_id")
    private Resume resume;

    private String name;

    private String organization;

    private String specialisation;

    @Column(name = "graduation_year")
    private String graduationYear;
}
