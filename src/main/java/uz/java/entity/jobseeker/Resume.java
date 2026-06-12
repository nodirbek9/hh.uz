package uz.java.entity.jobseeker;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import uz.java.entity.employer.Profession;
import uz.java.entity.enums.ResumeCreationType;
import uz.java.entity.enums.Status;
import uz.java.entity.user.Auditable;
import uz.java.entity.user.UserProfile;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@AllArgsConstructor
@NoArgsConstructor
@Entity
@Getter
@Setter
@Table(name = "resumes")
public class Resume extends Auditable {

    @ManyToOne
    @JoinColumn(name = "user_profile_id")
    private UserProfile userProfile;  // select * from Resume r inner join User u on u.id = r.user_id;


    @Enumerated(EnumType.STRING)
    private Status status;

    @Column(name = "about_me")
    private String aboutMe;

    @Column(name = "photo_path", length = 500)
    private String photoPath;

    @OneToMany(mappedBy = "resume")
    // faqat OneToMany boglanishda 2 ta turi bor Bidirectional va Unidirectional boglanishlar
    private List<Certificate> certificateList = new ArrayList<>();

    @ManyToMany
    @JoinTable(name = "resume_skills",
            joinColumns = {@JoinColumn(name = "resume_id")},
            inverseJoinColumns = {@JoinColumn(name = "skill_id")}
    )
    private Set<Skill> skills = new HashSet<>();

    @OneToMany(mappedBy = "resume", cascade = CascadeType.ALL)
    private List<WorkExperience> workExperienceList = new ArrayList<>();

    @OneToMany(mappedBy = "resume", cascade = CascadeType.ALL)
    private List<Education> educationList = new ArrayList<>();

    @Enumerated(EnumType.STRING)
    private ResumeCreationType type;

    @OneToOne
    @JoinColumn(name = "profession_id")
    private Profession profession;

    @OneToMany(mappedBy = "resume")
    private List<Course> courseList = new ArrayList<>();

    @OneToMany(mappedBy = "resume")
    private List<Portfolio> portfolioList = new ArrayList<>();
}
