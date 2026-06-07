package uz.java.entity.address;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import uz.java.entity.user.Auditable;

@Entity
@Table(name = "cities_of_country")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class CityAndCountry extends Auditable {

    private String name;

    private Long parentId;
}
