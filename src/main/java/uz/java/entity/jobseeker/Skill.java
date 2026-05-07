package uz.java.entity.jobseeker;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import uz.java.entity.enums.SkillLevel;
import uz.java.entity.user.Auditable;

@AllArgsConstructor
@NoArgsConstructor
@Entity
@Getter
@Setter
@Table(name = "skills")
public class Skill extends Auditable {

    private String name;

    private String code;

    @Enumerated(EnumType.STRING)
    private SkillLevel skillLevel;
}
