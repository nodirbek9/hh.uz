package uz.java.entity.jobseeker;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import uz.java.entity.enums.EducationDegree;
import uz.java.entity.user.Auditable;
import uz.java.entity.user.UserProfile;

@AllArgsConstructor
@NoArgsConstructor
@Entity
@Getter
@Setter
@Table(name = "educations")
public class Education extends Auditable {

    @ManyToOne
    @JoinColumn(name = "user_profile_id")
    private UserProfile userProfile;

    private String name;

    @Enumerated(EnumType.STRING)
    private EducationDegree degree;

    private String facultyName;

    private String speciality;

    @Column(name = "start_date")
    private String startDate;

    @Column(name = "end_date")
    private String endDate;
}
