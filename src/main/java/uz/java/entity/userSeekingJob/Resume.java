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
@Table(name = "resumes")
public class Resume {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 250)
    private String title;

    @Column(length = 1000)
    private String aboutMe;

    @Column(length = 250)
    private String currency;

    @Enumerated(EnumType.STRING)
    private Status status;

    private LocalDate createdAt;

    private LocalDate updatedAt;


    // vahokazo
}
