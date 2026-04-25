package uz.java.entity.jobseeker;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import uz.java.entity.enums.Status;
import uz.java.entity.user.Auditable;
import uz.java.entity.user.User;

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
    @JoinColumn(name = "user_id")
    private User user;  // select * from Resume r inner join User u on u.id = r.user_id;

    @Column(length = 100)
    private String profession;

    @Enumerated(EnumType.STRING)
    private Status status;

    @Column(name = "about_me")
    private String aboutMe;

    @OneToMany(mappedBy = "resume")// faqat OneToMany boglanishda 2 ta turi bor Bidirectional va Unidirectional boglanishlar
    private List<Certificate> certificateList = new ArrayList<>();

    @ManyToMany
    @JoinTable(name = "resume_skills",
            joinColumns = {@JoinColumn(name = "resume_id")},
            inverseJoinColumns = {@JoinColumn(name = "skill_id")}
    )
    private Set<Skill> skills = new HashSet<>();
}
