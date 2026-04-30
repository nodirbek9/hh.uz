package uz.java.entity.employer;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import uz.java.entity.enums.*;
import uz.java.entity.jobseeker.Skill;
import uz.java.entity.user.Auditable;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@AllArgsConstructor
@NoArgsConstructor
@Entity
@Getter
@Setter
@Table(name = "vacancies")
public class Vacancy extends Auditable {

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
    private SalaryFrequency salaryFrequency;

    @Enumerated(EnumType.STRING)
    private SearchKey searchKey;

    @Enumerated(EnumType.STRING)
    private RequiredVocational vocational;

    @Enumerated(EnumType.STRING)
    private WorkingHours workingHours;

    private WorkTimeSlot timeSlot;

    @Enumerated(EnumType.STRING)
    private EmploymentType employmentType;

    @Enumerated(EnumType.STRING)
    private Experience experience;

    private String city;

    @Enumerated(EnumType.STRING)
    private StatusVacancy status;

    private String location;

    @Column(name = "work_format")
    @Enumerated(EnumType.STRING)
    private WorkFormat  workFormat;

    @Enumerated(EnumType.STRING)
    private VacancyDisplay vacancyDisplay;

    @Column(name = "is_remote")
    private Boolean isRemote;

    @Column(name = "view_count")
    private Integer viewCount;

    private String contactName;
}
