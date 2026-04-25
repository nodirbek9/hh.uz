package uz.java.entity.jobseeker;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import uz.java.entity.user.Auditable;

@AllArgsConstructor
@NoArgsConstructor
@Entity
@Getter
@Setter
@Table(name = "portfolio_list")
public class Portfolio extends Auditable {

    @ManyToOne
    @JoinColumn(name = "resume_id")
    private Resume resume;

    private String image;
}
