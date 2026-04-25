package uz.java.entity.application;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import uz.java.entity.employer.Vacancy;
import uz.java.entity.enums.VacancyApplicationStatus;
import uz.java.entity.jobseeker.Resume;
import uz.java.entity.user.Auditable;
import uz.java.entity.user.User;

@AllArgsConstructor
@NoArgsConstructor
@Entity
@Data
@Table(name = "vacancy_applications")
public class VacancyApplication extends Auditable {

    @ManyToOne
    @JoinColumn(name = "vacancy_id")
    private Vacancy vacancy;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @OneToOne
    @JoinColumn(name = "resume_id")
    private Resume resume;

    private String letter;

    @Enumerated(EnumType.STRING)
    private VacancyApplicationStatus status;
}
