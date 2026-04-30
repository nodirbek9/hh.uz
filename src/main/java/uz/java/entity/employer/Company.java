package uz.java.entity.employer;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import uz.java.entity.user.Auditable;
import uz.java.entity.user.User;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@AllArgsConstructor
@NoArgsConstructor
@Entity
@Getter
@Setter
@Table(name = "companies")
public class Company extends Auditable {

    @OneToOne
    @JoinColumn(name = "owner_id")
    public User user;

    private String name;

    private String description;

    private String logo;

    private Long cityId;

    @Column(columnDefinition = "VARCHAR DEFAULT 998 ")
    private String phone;

    @Column(unique = true)
    private String email;

    @Column(name = "is_verified")
    private String isVerified;

    @ElementCollection
    private List<String> imageUrls = new ArrayList<>();

    @ManyToMany
    @JoinTable(name = "company_industries",
            joinColumns = {@JoinColumn(name = "company_id")},
            inverseJoinColumns = {@JoinColumn(name = "company_industry_id")}
    )
    private Set<CompanyIndustry> companyIndustry = new HashSet<>();
}
