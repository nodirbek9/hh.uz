package uz.java.entity.subscription;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import uz.java.entity.employer.Company;
import uz.java.entity.enums.SubscriptionPlan;
import uz.java.entity.user.Auditable;

@AllArgsConstructor
@NoArgsConstructor
@Entity
@Data
@Table(name = "subscription_companies")
public class SubscriptionCompany extends Auditable {

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
