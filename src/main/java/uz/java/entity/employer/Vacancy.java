package uz.java.entity.employer;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import uz.java.entity.enums.CurrencyType;
import uz.java.entity.enums.EmploymentType;
import uz.java.entity.enums.Experience;
import uz.java.entity.enums.StatusVacancy;
import uz.java.entity.jobseeker.Skill;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@AllArgsConstructor
@NoArgsConstructor
@Entity
@Getter
@Setter
@Table(name = "vacancies")
public class Vacancy {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "company_id")
    private Company company;

    @ManyToMany
    @JoinTable(name = "vacancy_skills",
            joinColumns = {@JoinColumn(name = "vacancy_id")},
            inverseJoinColumns = {@JoinColumn(name = "skill_id")}
    )
    private Set<Skill> skills = new HashSet<>();

    @ManyToMany
    @JoinTable(name = "vacancy_tags",
            joinColumns = {@JoinColumn(name = "vacancy_id")},
            inverseJoinColumns = {@JoinColumn(name = "tag_id")}
    )
    private Set<Tag> tags = new HashSet<>();

    private String name;

    private String description;

    private Integer salary;

    @Enumerated(EnumType.STRING)
    private CurrencyType currency;

    @Enumerated(EnumType.STRING)
    private EmploymentType employmentType;

    @Enumerated(EnumType.STRING)
    private Experience experience;

    private String city;

    @Enumerated(EnumType.STRING)
    private StatusVacancy status;

    private String location;

    @Column(name = "is_remote")
    private Boolean isRemote;

    @Column(name = "view_count")
    private Integer viewCount;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    private String contactName;
}
