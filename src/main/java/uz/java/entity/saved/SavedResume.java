package uz.java.entity.saved;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import uz.java.entity.jobseeker.Resume;
import uz.java.entity.user.UserProfile;

@AllArgsConstructor
@NoArgsConstructor
@Entity
@Getter
@Setter
@Table(name = "saved_resumes")
public class SavedResume {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private UserProfile user;

    @OneToOne
    @JoinColumn(name = "resume_id")
    private Resume resume;

    @Column(name = "saved_at")
    private String savedAt;
}
