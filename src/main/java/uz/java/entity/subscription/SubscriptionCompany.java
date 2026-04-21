package uz.java.entity.subscription;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import uz.java.entity.employer.Company;
import uz.java.entity.enums.SubscriptionPlan;

@AllArgsConstructor
@NoArgsConstructor
@Entity
@Data
@Table(name = "subscription_companies")
public class SubscriptionCompany {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "company_id")
    private Company company;

    @Enumerated(EnumType.STRING)
    private SubscriptionPlan plan;

    @Column(name = "start_date")
    private String startDate;

    @Column(name = "end_date")
    private String endDate;

    @Column(name = "is_Active")
    private Boolean isActive;
}
