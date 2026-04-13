package uz.java.entity.userSeekingJob;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.Date;

@AllArgsConstructor
@NoArgsConstructor
@Entity
@Getter
@Setter
@Table(name = "educations")
public class Education {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 250)
    private String institution;

    @Column(length = 250)
    private String degree;

    @Column(length = 1000)
    private String field;

    private LocalDate birthDate;

    private LocalDate startDate;

    private LocalDate endDate;

    @Column(length = 1000)
    private String description;
}
