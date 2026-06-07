package uz.java.entity.employer;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import uz.java.entity.user.Auditable;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
@Table(name = "professions")
public class Profession extends Auditable {

    private String name;

    private Long parentId;
}
