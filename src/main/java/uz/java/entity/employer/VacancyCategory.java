package uz.java.entity.employer;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import uz.java.entity.user.Auditable;

@AllArgsConstructor
@NoArgsConstructor
@Entity
@Data
@Table(name = "vacancy_categories")
public class VacancyCategory extends Auditable {

    private String name;

    @Column(name = "parent_id")
    private String parentId;

    private String iconUrl;
}