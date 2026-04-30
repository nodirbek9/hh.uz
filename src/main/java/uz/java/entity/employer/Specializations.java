package uz.java.entity.employer;

import jakarta.persistence.*;
import lombok.*;
import uz.java.entity.user.Auditable;

@AllArgsConstructor
@NoArgsConstructor
@Entity
@Getter
@Setter
@Table(name = "specializations")
public class Specializations extends Auditable {

    private String name;

    @Column(name = "parent_id")
    private String parentId;
}