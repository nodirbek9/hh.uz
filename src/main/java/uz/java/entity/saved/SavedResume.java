package uz.java.entity.saved;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import uz.java.entity.jobseeker.Resume;
import uz.java.entity.user.Auditable;
import uz.java.entity.user.UserProfile;

@AllArgsConstructor
@NoArgsConstructor
@Entity
@Getter
@Setter
@Table(name = "saved_resumes")
public class SavedResume extends Auditable {

    @ManyToOne
    @JoinColumn(name = "user_id")
    private UserProfile user;

    @OneToOne
    @JoinColumn(name = "resume_id")
    private Resume resume;
}
