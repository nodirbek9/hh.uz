package uz.java.entity.userSeekingJob;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Getter
@Setter
@Table(name = "workExperinces")
public class WorkExperience {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 250)
    private String companyName;

    @Column(length = 250)
    private String position;

    private LocalDate startDate;

    private LocalDate endDate;

    private LocalDate isCurrent;

    @Column(length = 1000)
    private String description;
}
