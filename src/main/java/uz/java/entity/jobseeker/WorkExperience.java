package uz.java.entity.jobseeker;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import uz.java.entity.user.Auditable;
import uz.java.entity.user.UserProfile;

@AllArgsConstructor
@NoArgsConstructor
@Entity
@Getter
@Setter
@Table(name = "workExperinces")
public class WorkExperience extends Auditable {

    @ManyToOne
    @JoinColumn(name = "resume_id")
    private Resume resume;

    @ManyToOne
    @JoinColumn(name = "user_profile_id")
    private UserProfile userProfile;

    @Column(name = "company_name")
    private String companyName;

    @Column(name = "job_tile")
    private String jobTitle;

    private String description;

    @Column(name = "start_date")
    private String startDate;

    @Column(name = "end_date")
    private String endDate;

    @Column(name = "is_current")
    private Boolean isCurrent;
}