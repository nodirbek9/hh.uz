package uz.java.entity.jobseeker;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import uz.java.entity.enums.LanguageLevel;
import uz.java.entity.user.Auditable;
import uz.java.entity.user.UserProfile;

@AllArgsConstructor
@NoArgsConstructor
@Entity
@Getter
@Setter
@Table(name = "languages")
public class Language extends Auditable {

    @ManyToOne
    @JoinColumn(name = "user_profile_id")
    private UserProfile userProfile;

    private String name;

    @Enumerated(EnumType.STRING)
    private LanguageLevel level;
}
