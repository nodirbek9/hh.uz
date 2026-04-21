package uz.java.entity.user;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import uz.java.entity.enums.Gender;
import uz.java.entity.jobseeker.Language;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

@AllArgsConstructor
@NoArgsConstructor
@Entity
@Getter
@Setter
@Table(name = "userProfiles")
public class UserProfile {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "user_id")
    private Long userId;

    private String avatar;

    @Column(name = "first_name")
    private String firstName;

    @Column(name = "last_name")
    private String lastName;

    @Column(name = "birthday_date")
    private LocalDate birthDate;

    @Enumerated(EnumType.STRING)
    private Gender gender;

    private String city;

    private String country;

    @ManyToMany
    @JoinTable(name = "user_profile_language",
            joinColumns = {@JoinColumn(name = "user_profile_id")},
            inverseJoinColumns = {@JoinColumn(name = "language_id")}
    )
    private Set<Language> languages = new HashSet<>();
}
